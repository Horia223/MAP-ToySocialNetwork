package repository;

import domain.User;

import java.util.HashMap;

public class UserRepository {
    private final HashMap<Integer, User> users = new HashMap<>();
    private int nextId = 1;




    public void remove(int id){
        if(users.remove(id) == null){
            throw new RuntimeException("Error: Cannot remove a user that does not exist.");
        }
    }

    public User findById(int id){
        User user = users.get(id);
        if(user == null){
            throw new RuntimeException("Error: The user does not exists.");
        }
        return user;
    }

    public User add(String firstname, String lastname){
        User user = new User(nextId, firstname, lastname);
        users.put(user.getId(),user);
        nextId++;
        return user;

    }

}
