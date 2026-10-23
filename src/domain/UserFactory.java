package domain;
/**
 * Creates users and assigns them sequential IDs.
 */
public class UserFactory {
    private int nextId = 1;

    public User createUser(String firstName, String lastName) {
        return new User(nextId++, firstName, lastName);
    }
}
