package domain;

import java.util.Collection;

/**
 * Creates users and assigns them sequential IDs.
 */
public class UserFactory {
    private long nextId = 1L;

    public UserFactory(Collection<User> existingUsers) {
        for (User user : existingUsers) {
            if (user.getId() >= nextId) {
                nextId = user.getId() + 1;
            }
        }
    }

    public User createUser(String firstName, String lastName) {
        return new User(nextId++, firstName, lastName);
    }
}
