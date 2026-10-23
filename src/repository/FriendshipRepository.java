package repository;


import domain.Friendship;
import exception.EntityNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
/**
 * Stores friendships in memory and prevents duplicate friendships.
 */
public class FriendshipRepository {
    private final HashSet<Friendship> friendships = new HashSet<>();


    public Collection<Friendship> getAll(){
        return new HashSet<>(friendships);
    }
    /**
     * Adds a friendship to the repository.
     *
     * @param friendship the friendship to add
     * @throws IllegalArgumentException if the friendship is null or already exists
     */
    public void add(Friendship friendship){
        if(friendship == null) throw new IllegalArgumentException("Cannot add a friendship that is null.");
        if(!friendships.add(friendship)) throw new IllegalArgumentException("Cannot add duplicates.");
    }
    /**
     * Removes an existing friendship.
     *
     * @param friendship the friendship to remove
     * @throws EntityNotFoundException if the friendship does not exist
     */
    public void remove(Friendship friendship){
        if(friendship == null) throw new EntityNotFoundException("Cannot remove friendship that is null.");
        if(!friendships.remove(friendship)) throw new EntityNotFoundException("Cannot remove a friendship that does not exist.");
    }
    /**
     * Removes every friendship involving the specified user.
     *
     * @param userid the ID of the user whose friendships are removed
     */
    public void removeAllWithId(int userid){
        Iterator<Friendship> it = friendships.iterator();

        while (it.hasNext()) {
            Friendship f = it.next();
            if(f.getUserId1() == userid || f.getUserId2() == userid) {
                it.remove();
            }
        }
        //or we could use a for loop with a temporary collection, and then use friendships.removeAll(toRemove), the easier method
    }


}
