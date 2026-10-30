package repository;

import domain.User;
import exception.EntityNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
/**
 * Stores users in memory using a map indexed by user ID.
 */
public class UserRepository implements Repository<Long, User>
{
    private final HashMap<Long, User> users = new HashMap<>();


    public Collection<User> getAll() {
        return new HashMap<>(users).values();
    }

    @Override
    public void remove(Long id){
        if(users.remove(id) == null){
            throw new EntityNotFoundException("Cannot remove a user that does not exist.");
        }
    }

    @Override
    public User findById(Long id) {
        User user = users.get(id);

        if(user == null){
            throw new EntityNotFoundException("The user does not exist.");
        }

        return user;
    }

    /**
     * Adds a user if its ID is not already present.
     *
     * @param user the user to add
     * @return the added user
     * @throws IllegalArgumentException if the ID already exists
     */
    @Override
    public User add(User user){
        if(users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User ID already exists.");
        }

        users.put(user.getId(),user);
        return user;

    }


}
