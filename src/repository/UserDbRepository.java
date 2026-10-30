package repository;

import domain.User;
import validation.Validator;

import java.util.Collection;

public class UserDbRepository implements Repository<Long, User> {
    private final Validator<User> validator;
    private final String url;
    private final String user;
    private final String password;

    public UserDbRepository(
            Validator<User> validator,
            String url,
            String user,
            String password) {
        this.validator = validator;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public User save(User entity) {
        throw new UnsupportedOperationException(
                "Not yet");
    }

    @Override
    public User delete(Long id) {
        throw new UnsupportedOperationException(
                "Not yet");
    }

    @Override
    public User findOne(Long id) {
        throw new UnsupportedOperationException(
                "Not yet");
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException(
                "Not yet");
    }

    @Override
    public Collection<User> getAll() {
        throw new UnsupportedOperationException(
                "Not yet");
    }
}