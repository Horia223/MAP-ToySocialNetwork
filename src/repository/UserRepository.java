package repository;

import domain.User;
import exception.EntityNotFoundException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class UserRepository implements Repository<Integer, User>
{
    private final HashMap<Integer, User> users = new HashMap<>();


    public Collection<User> getAll() {
        return new HashMap<>(users).values();
    }

    @Override
    public void remove(Integer id){
        if(users.remove(id) == null){
            throw new EntityNotFoundException("Cannot remove a user that does not exist.");
        }
    }

    @Override
    public User findById(Integer id) {
        User user = users.get(id);

        if(user == null){
            throw new EntityNotFoundException("The user does not exist.");
        }

        return user;
    }


    @Override
    public User add(User user){
        if(users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User ID already exists.");
        }

        users.put(user.getId(),user);
        return user;

    }


}
