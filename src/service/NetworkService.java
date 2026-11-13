package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import domain.UserFactory;
import repository.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import exception.EntityNotFoundException;

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



    /**
     * Finds users that match the given filter.
     *
     * @param filter the condition users must satisfy
     * @return a list of matching users
     * @throws NullPointerException if the filter is null
     */

    public List<User> findUsers(Predicate<User> filter) {
        return userRepository.findAll(filter);
    }

    public Collection<Friendship> getAllFriendships() {
        return friendshipRepository.getAll();
    }


    /**
     * Returns all users who are friends with the given user.
     *
     * @param userId the ID of the user
     * @return a list of the user's friends
     * @throws exception.EntityNotFoundException if the user does not exist
     */
    public List<User> getFriends(Long userId) {
        requireUser(userId);

        return friendshipRepository.getAll().stream()
                .filter(friendship ->
                        friendship.getUserId1().equals(userId)
                                || friendship.getUserId2().equals(userId))
                .map(friendship ->
                        friendship.getUserId1().equals(userId)
                                ? friendship.getUserId2()
                                : friendship.getUserId1())
                .map(this::requireUser)
                .collect(Collectors.toList());
    }

    public User addUser(String firstname, String lastname){


        User user = userFactory.createUser(firstname,lastname);
        return userRepository.save(user);

    }


    private User requireUser(Long id){
        return userRepository.findOne(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public void addFriendship(Long userId, Long userId2){


        requireUser(userId);
        requireUser(userId2);

        Friendship friendship = new Friendship(userId, userId2);
        friendshipRepository.save(friendship);


    }

    public void removeUser(Long id) {
        requireUser(id);

        List<Tuple<Long, Long>> friendshipIdsToRemove =
                friendshipRepository.getAll().stream()
                        .filter(friendship ->
                                friendship.getUserId1().equals(id)
                                        || friendship.getUserId2().equals(id))
                        .map(Friendship::getId)
                        .collect(Collectors.toList());

        friendshipIdsToRemove.forEach(friendshipRepository::delete);
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
