package repository;

import domain.User;
import validation.Validator;
/**
 * Stores users in memory using a map indexed by user ID.
 */


public class UserRepository extends InMemoryRepository<Long, User> {
    public UserRepository(Validator<User> validator) {
        super(validator);
    }
}
