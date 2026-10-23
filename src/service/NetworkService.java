package service;

import domain.Friendship;
import domain.User;
import domain.UserFactory;
import repository.Repository;
import repository.FriendshipRepository;
import validation.Validator;
import validation.UserValidator;
import validation.FriendshipValidator;

public class NetworkService {

    private final Repository<Integer, User> userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserFactory userFactory = new UserFactory();
    private final Validator<User> userValidator = new UserValidator();
    private final Validator<Friendship> friendshipValidator =  new FriendshipValidator();



    public NetworkService(Repository<Integer, User> userRepository, FriendshipRepository friendshipRepository){
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public User addUser(String firstname, String lastname){



        User user = userFactory.createUser(firstname,lastname);
        userValidator.validate(user);
        return userRepository.add(user);

    }

    public void addFriendship(int userId, int userId2){


        userRepository.findById(userId);
        userRepository.findById(userId2);

        Friendship friendship = new Friendship(userId, userId2);
        friendshipValidator.validate(friendship);
        friendshipRepository.add(friendship);


    }

    public void removeUser(int id){


        userRepository.findById(id);   //validation

        friendshipRepository.removeAllWithId(id);
        userRepository.remove(id);

    }

    public void removeFriendship(int userid1, int userid2){
        //need to implement the validation


        Friendship friendship = new Friendship(userid1, userid2);
        friendshipRepository.remove(friendship);
    }
}
