package service;

import domain.Friendship;
import domain.User;
import domain.UserFactory;
import repository.Repository;
import repository.FriendshipRepository;
import validation.Validator;
import validation.UserDataValidator;
import validation.FriendshipValidator;

import java.util.Collection;
import java.util.Set;

public class NetworkService {

    private final Repository<Long, User> userRepository;
    private final FriendshipRepository friendshipRepository;
    private final UserFactory userFactory = new UserFactory();
    private final Validator<User> userValidator = new UserDataValidator();
    private final Validator<Friendship> friendshipValidator =  new FriendshipValidator();



    public NetworkService(Repository<Long, User> userRepository, FriendshipRepository friendshipRepository){
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }


    public Collection<User> getAllUsers() {
        return userRepository.getAll();
    }

    public Collection<Friendship> getAllFriendships() {
        return friendshipRepository.getAll();
    }

    public User addUser(String firstname, String lastname){


        User user = userFactory.createUser(firstname,lastname);
        userValidator.validate(user);
        return userRepository.add(user);

    }

    public void addFriendship(Long userId, Long userId2){


        userRepository.findById(userId);
        userRepository.findById(userId2);

        Friendship friendship = new Friendship(userId, userId2);
        friendshipValidator.validate(friendship);
        friendshipRepository.add(friendship);


    }

    public void removeUser(Long id){


        userRepository.findById(id);   //validation

        friendshipRepository.removeAllWithId(id);
        userRepository.remove(id);

    }

    public void removeFriendship(Long userid1, Long userid2){


        Friendship friendship = new Friendship(userid1, userid2);
        friendshipRepository.remove(friendship);
    }

    //Methods that imply graphs
    /**
     * Counts the connected components in the social network.
     *
     * @return the number of connected components
     */
    public int countCommunities() {
        NetworkGraph graph = new NetworkGraph(userRepository.getAll(), friendshipRepository.getAll()
        );

        return graph.countCommunities();
    }

    public Set<Long> getMostSociableCommunity() {
        NetworkGraph graph = new NetworkGraph(userRepository.getAll(), friendshipRepository.getAll()
        );

        return graph.mostSociableCommunity();
    }
}
