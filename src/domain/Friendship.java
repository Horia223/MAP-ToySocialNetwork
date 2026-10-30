package domain;

import java.time.LocalDateTime;
/**
 * Represents an undirected friendship between two users.
 * The user IDs are stored in ascending order so that
 * a friendship is independent of the order of its users.
 */

public class Friendship extends Entity<Tuple<Long, Long>> {
    private final LocalDateTime date;

    public Long getUserId1() {
        return getId().getFirst();
    }

    public Long getUserId2() {

        return getId().getSecond();
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Creates a friendship between two distinct users.
     *
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @throws IllegalArgumentException if both IDs are equal
     */
    public Friendship(Long userId1, Long userId2) {
        this(userId1, userId2, LocalDateTime.now());
    }

    public Friendship(Long userId1, Long userId2, LocalDateTime date) {
        super(createId(userId1, userId2));
        if (date == null) {
            throw new IllegalArgumentException("Friendship date cannot be null.");
        }
        this.date = date;
    }

    private static Tuple<Long, Long> createId(Long userId1, Long userId2) {
        if (userId1 == null || userId2 == null) {
            throw new IllegalArgumentException("User IDs cannot be null.");
        }
        if (userId1.equals(userId2)) {
            throw new IllegalArgumentException("A user cannot be friends with themselves.");
        }
        if (userId1 < userId2) {
            return new Tuple<>(userId1, userId2);
        }
        return new Tuple<>(userId2, userId1);
    }


}
