package repository;


import domain.Friendship;

import java.util.HashSet;

public class FriendshipRepository {
    private final HashSet<Friendship> friendships = new HashSet<>();


    public void add(Friendship friendship){
        if(friendship == null) throw new IllegalArgumentException("Cannot add a friendship that is null.");
        if(!friendships.add(friendship)) throw new IllegalArgumentException("Cannot add duplicates.");
    }

    public void remove(Friendship friendship){
        if(friendship == null) throw new IllegalArgumentException("Cannot remove friendship that is null.");
        if(!friendships.remove(friendship)) throw new IllegalArgumentException("Cannot remove a friendship that does not exist.");
    }


}
