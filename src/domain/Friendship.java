package domain;
/**
 * Represents an undirected friendship between two users.
 * The user IDs are stored in ascending order so that
 * a friendship is independent of the order of its users.
 */

public class Friendship extends Entity<Tuple<Integer, Integer>> {

    public int getUserId1() {
        return getId().getFirst();
    }

    public int getUserId2() {

        return getId().getSecond();
    }

    /**
     * Creates a friendship between two distinct users.
     *
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @throws IllegalArgumentException if both IDs are equal
     */
    public Friendship(int userId1, int userId2){
        super(createId(userId1, userId2));
    }

    private static Tuple<Integer, Integer> createId(int userId1, int userId2) {
        if (userId1 == userId2) {
            throw new IllegalArgumentException("A user cannot be friends with themselves.");
        }
        if (userId1 <= userId2) {
            return new Tuple<>(userId1, userId2);
        }
        return new Tuple<>(userId2, userId1);
    }


}
