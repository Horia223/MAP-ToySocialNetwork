package repository;

import domain.Friendship;
import domain.Tuple;
import validation.Validator;

import java.util.Collection;
import java.util.Optional;

public class FriendshipDbRepository
        implements Repository<Tuple<Long, Long>, Friendship> {

    private final Validator<Friendship> validator;
    private final String url;
    private final String user;
    private final String password;

    public FriendshipDbRepository(
            Validator<Friendship> validator,
            String url,
            String user,
            String password) {
        this.validator = validator;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Friendship save(Friendship entity) {
        throw new UnsupportedOperationException(
                "Not yet");
    }


    @Override
    public Friendship delete(Tuple<Long, Long> id) {
        throw new UnsupportedOperationException(
                "Not yet");
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> id) {
        throw new UnsupportedOperationException("Not yet");}


    @Override
    public Friendship update(Friendship entity) {
        throw new UnsupportedOperationException("Not yet");
    }



    @Override
    public Collection<Friendship> getAll() {
        throw new UnsupportedOperationException(
                "Not yet");
    }
}