package repository;


import domain.Friendship;
import exception.EntityNotFoundException;

import java.util.HashSet;
import java.util.Iterator;

public class FriendshipRepository {
    private final HashSet<Friendship> friendships = new HashSet<>();


    public void add(Friendship friendship){
        if(friendship == null) throw new IllegalArgumentException("Cannot add a friendship that is null.");
        if(!friendships.add(friendship)) throw new IllegalArgumentException("Cannot add duplicates.");
    }

    public void remove(Friendship friendship){
        if(friendship == null) throw new EntityNotFoundException("Cannot remove friendship that is null.");
        if(!friendships.remove(friendship)) throw new EntityNotFoundException("Cannot remove a friendship that does not exist.");
    }

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
