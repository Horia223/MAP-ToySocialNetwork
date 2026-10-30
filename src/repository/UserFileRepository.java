package repository;

import domain.User;
import validation.Validator;

import java.nio.file.Path;

public class UserFileRepository extends AbstractFileRepository<Long, User> {
    public UserFileRepository(Path file, Validator<User> validator) {
        super(file, validator);
    }

    @Override
    protected User extractEntity(String line) {
        String[] fields = line.split(";", -1);
        if (fields.length != 3) {
            throw new IllegalArgumentException("Expected 3 fields for a user.");
        }
        return new User(Long.parseLong(fields[0]), fields[1], fields[2]);
    }

    @Override
    protected String createEntityAsString(User user) {
        return user.getId() + ";" + user.getFirstName() + ";" + user.getLastName();
    }
}
