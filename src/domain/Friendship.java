package domain;
import java.util.Objects;

/**
 * Represents an undirected friendship between two users.
 * The user IDs are stored in ascending order so that
 * a friendship is independent of the order of its users.
 */

public class Friendship
{
    private final int userId1;
    private final int userId2;

    public int getUserId1() {
        return userId1;
    }

    public int getUserId2() {

        return userId2;
    }

    /**
     * Creates a friendship between two distinct users.
     *
     * @param userId1 the ID of the first user
     * @param userId2 the ID of the second user
     * @throws IllegalArgumentException if both IDs are equal
     */
    public Friendship(int userId1, int userId2){
        if (userId1 == userId2){
            throw new IllegalArgumentException(
                    "A user cannot be friends with themselves."
            );
        }

            if(userId1 <= userId2) {
            this.userId1 = userId1;
            this.userId2 = userId2;
        }
        else{
            this.userId1 = userId2;
            this.userId2 = userId1;
        }
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj == null || getClass() != obj.getClass()){
            return false;
        }

        Friendship other = (Friendship) obj;

        return userId1 == other.userId1 && userId2 == other.userId2;
    }

    @Override
    public int hashCode(){

        return Objects.hash(userId1,userId2);
    }


}
