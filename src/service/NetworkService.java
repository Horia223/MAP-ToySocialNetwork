package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.UserFactory;
import repository.Repository;

import java.util.Collection;
import java.util.Set;

public class NetworkService {

    private final Repository<Long, User> userRepository;
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;
    private final UserFactory userFactory;



    public NetworkService(Repository<Long, User> userRepository,
                          Repository<Tuple<Long, Long>, Friendship> friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.userFactory = new UserFactory(userRepository.getAll());
    }


    public Collection<User> getAllUsers() {
        return userRepository.getAll();
    }

    public Collection<Friendship> getAllFriendships() {
        return friendshipRepository.getAll();
    }

    public User addUser(String firstname, String lastname){


        User user = userFactory.createUser(firstname,lastname);
        return userRepository.save(user);

    }

    public void addFriendship(Long userId, Long userId2){


        userRepository.findOne(userId);
        userRepository.findOne(userId2);

        Friendship friendship = new Friendship(userId, userId2);
        friendshipRepository.save(friendship);


    }

    public void removeUser(Long id){


        userRepository.findOne(id);
        for (Friendship friendship : friendshipRepository.getAll()) {
            if (friendship.getUserId1().equals(id) || friendship.getUserId2().equals(id)) {
                friendshipRepository.delete(friendship.getId());
            }
        }
        userRepository.delete(id);

    }

    public void removeFriendship(Long userid1, Long userid2){


        Friendship friendship = new Friendship(userid1, userid2);
        friendshipRepository.delete(friendship.getId());
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
