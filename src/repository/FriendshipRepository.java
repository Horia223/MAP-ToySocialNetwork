package repository;


import domain.Friendship;
import domain.Tuple;
import validation.Validator;
/**
 * Stores friendships in memory and prevents duplicate friendships.
 */
public class FriendshipRepository extends InMemoryRepository<Tuple<Long, Long>, Friendship> {
    public FriendshipRepository(Validator<Friendship> validator) {
        super(validator);
    }
}
