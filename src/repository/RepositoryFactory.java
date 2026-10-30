package repository;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import exception.RepositoryException;
import validation.FriendshipValidator;
import validation.UserDataValidator;
import validation.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class RepositoryFactory {
    private final Properties properties = new Properties();
    private final RepositoryType repositoryType;

    public RepositoryFactory(Path configFile) {
        try (InputStream input = Files.newInputStream(configFile)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RepositoryException("Could not read configuration file.", e);
        }

        String type = properties.getProperty("repository.type");

        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Missing repository.type in configuration.");
        }

        try {
            repositoryType = RepositoryType.valueOf(type.trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown repository.type: " + type, e);
        }
    }

    public Repository<Long, User> createUserRepository(Validator<User> validator) {
        switch (repositoryType) {
            case MEMORY:
                return new UserRepository(validator);
            case FILE:
                return new UserFileRepository(
                        Paths.get(required("user.file")), validator);



                case DATABASE:
                return new UserDbRepository(
                        validator,
                        required("db.url"),
                        required("db.user"),
                        required("db.password"));
            default:
                throw new IllegalArgumentException("Unknown repository type.");
        }
    }

    public Repository<Tuple<Long, Long>, Friendship> createFriendshipRepository(
            Validator<Friendship> validator) {
        switch (repositoryType) {
            case MEMORY:
                return new FriendshipRepository(validator);

                case FILE:
                return new FriendshipFileRepository(
                        Paths.get(required("friendship.file")), validator);


                case DATABASE:
                return new FriendshipDbRepository(
                        validator,
                        required("db.url"),
                        required("db.user"),
                        required("db.password"));
            default:
                throw new IllegalArgumentException("Unknown repository type.");
        }
    }

    private String required(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing configuration property: " + key);
        }

        return value.trim();
    }
}