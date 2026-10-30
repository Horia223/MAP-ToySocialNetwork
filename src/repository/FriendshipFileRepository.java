package repository;

import domain.Friendship;
import domain.Tuple;
import validation.Validator;

import java.nio.file.Path;
import java.time.LocalDateTime;

public class FriendshipFileRepository
        extends AbstractFileRepository<Tuple<Long, Long>, Friendship> {

    public FriendshipFileRepository(Path file, Validator<Friendship> validator) {
        super(file, validator);
    }

    @Override
    protected Friendship extractEntity(String line) {
        String[] fields = line.split(";", -1);
        if (fields.length != 3) {
            throw new IllegalArgumentException("Expected 3 fields for a friendship.");
        }
        Long userId1 = Long.parseLong(fields[0]);
        Long userId2 = Long.parseLong(fields[1]);
        LocalDateTime date = LocalDateTime.parse(fields[2]);
        return new Friendship(userId1, userId2, date);
    }

    @Override
    protected String createEntityAsString(Friendship friendship) {
        return friendship.getUserId1() + ";" + friendship.getUserId2() + ";" + friendship.getDate();
    }
}
