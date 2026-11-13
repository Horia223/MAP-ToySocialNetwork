package domain;

import java.util.Collection;

/**
 * Creates users and assigns them sequential IDs.
 */
public class UserFactory {
    private long nextId = 1L;

    public UserFactory(Collection<User> existingUsers) {
        nextId = existingUsers.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0L) + 1;
    }

    public User createUser(String firstName, String lastName) {
        return new User(nextId++, firstName, lastName);
    }
}
