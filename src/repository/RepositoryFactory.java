package repository;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import exception.RepositoryException;
//import validation.FriendshipValidator;
//import validation.UserDataValidator;
import validation.Validator;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;


public class RepositoryFactory {
    private final Properties properties = new Properties();
    private final RepositoryType repositoryType;
    private final Map<RepositoryType, Function<Validator<User>, Repository<Long,User>>>  userRepositoryFactories;
    private final Map<RepositoryType, Function<Validator<Friendship>, Repository<Tuple<Long,Long>, Friendship>>> friendshipRepositoryFactories;


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

        userRepositoryFactories = new EnumMap<>(RepositoryType.class);
        userRepositoryFactories.put(RepositoryType.MEMORY, UserRepository::new);
        userRepositoryFactories.put(
                RepositoryType.FILE,
                validator -> new UserFileRepository(
                        Paths.get(required("user.file")),
                        validator
                )
        );
        userRepositoryFactories.put(
                RepositoryType.DATABASE,
                validator ->  new UserDbRepository(
                        validator,
                        required("db.url"),
                        required("db.user"),
                        required("db.password")
                )
        );
        friendshipRepositoryFactories = new EnumMap<>(RepositoryType.class);
        friendshipRepositoryFactories.put(RepositoryType.MEMORY, FriendshipRepository::new);
        friendshipRepositoryFactories.put(
                RepositoryType.FILE,
                validator -> new FriendshipFileRepository(
                        Paths.get(required("friendship.file")),
                        validator
                )
        );
        friendshipRepositoryFactories.put(
                RepositoryType.DATABASE,
                validator -> new FriendshipDbRepository(
                        validator,
                        required("db.url"),
                        required("db.user"),
                        required("db.password")
                        )
        );
    }

    public Repository<Long, User> createUserRepository(Validator<User> validator) {

        Function<Validator<User>, Repository<Long, User>> factory = userRepositoryFactories.get(repositoryType);

        if(factory == null){
            throw new IllegalArgumentException("Unknown repository type: " + repositoryType);
        }
        return factory.apply(validator);
    }

    public Repository<Tuple<Long, Long>, Friendship> createFriendshipRepository(
            Validator<Friendship> validator) {

        Function<Validator<Friendship>,
                Repository<Tuple<Long, Long>, Friendship>> factory = friendshipRepositoryFactories.get(repositoryType);

        if (factory == null) {
            throw new IllegalArgumentException("Unknown repository type: " + repositoryType);
        }
        return factory.apply(validator);
    }

    private String required(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Missing configuration property: " + key);
        }

        return value.trim();
    }
}