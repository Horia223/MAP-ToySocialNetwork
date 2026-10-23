package repository;

import domain.User;

import java.util.HashMap;
import java.util.Optional;

public class UserRepository implements Repository<Integer, User>
{
    private final HashMap<Integer, User> users = new HashMap<>();

    @Override
    public void remove(Integer id){
        if(users.remove(id) == null){
            throw new RuntimeException("Error: Cannot remove a user that does not exist.");
        }
    }

    @Override
    public User findById(Integer id) {
        User user = users.get(id);

        if(user == null){
            throw new RuntimeException("The user does not exist.");
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
