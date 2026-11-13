
package service;

import domain.Friendship;
import domain.User;

import java.util.*;
import java.util.stream.Collectors;

//lista ad
public class NetworkGraph {
    private final Map<Long, Set<Long>> adjacencyList = new HashMap<>();

    public NetworkGraph(Collection<User> users, Collection<Friendship> friendships) {

        users.stream()
                .forEach(user ->
                        adjacencyList.put(user.getId(), new HashSet<>())
                );

        friendships.stream()
                .forEach(friendship -> {
                    Long id1 = friendship.getUserId1();
                    Long id2 = friendship.getUserId2();

                    adjacencyList.get(id1).add(id2);
                    adjacencyList.get(id2).add(id1);
                });
    }

    /**
     * Counts the connected components in the social network.
     *
     * @return the number of communities
     */

    public int countCommunities() {
        return findCommunities().size();
    }

    private void dfs(
        Long userId,
        Long componentId,
        Map<Long, Long> componentIds)

    {
            if (componentIds.containsKey(userId)) {
                return;
            }

            componentIds.put(userId, componentId);

            adjacencyList.get(userId).stream()
                    .forEach(neighbour ->
                            dfs(neighbour, componentId, componentIds)
                    );
    }

    /**
     * Finds the connected component with the largest diameter.
     * The diameter is calculated using breadth-first search.
     *
     * @return the user IDs of the selected component,
     *         or an empty set if the graph contains no vertices
     */
    public Set<Long> mostSociableCommunity() {
        return findCommunities().values().stream()
                .max(Comparator.comparingInt(this::diameter))
                .orElseGet(HashSet::new);
        }



    private Map<Long, Long> findComponentIds() {
        Map<Long, Long> componentIds = new HashMap<>();

        adjacencyList.keySet().stream()
                .forEach(userId -> dfs(userId, userId, componentIds));

        return componentIds;
    }

    /**
     * Groups users into connected components.
     *
     * @return a map from component root ID to its user IDs
     */
    private Map<Long, Set<Long>> findCommunities() {
        return findComponentIds().entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getValue,
                        Collectors.mapping(
                                Map.Entry::getKey,
                                Collectors.toSet()
                        )
                ));
    }
    /**
     * Calculates the shortest-path distances from one user to all reachable users.
     *
     * @param start the starting user ID
     * @return a map from reachable user ID to distance from the start
     */
    private Map<Long, Integer> bfs(Long start) {
        Map<Long, Integer> distances = new HashMap<>();
        distances.put(start, 0);

        Set<Long> currentLevel = new HashSet<>();
        currentLevel.add(start);

        bfsNextLevel(currentLevel, distances, 1);
        return distances;
    }



    /**
     * Processes one BFS level and recursively processes the next one.
     *
     * @param currentLevel the users at the current distance
     * @param distances the distances already discovered
     * @param nextDistance the distance assigned to the next level
     */
    private void bfsNextLevel(
            Set<Long> currentLevel,
            Map<Long, Integer> distances,
            int nextDistance) {
        Set<Long> nextLevel = currentLevel.stream()
                .flatMap(userId -> adjacencyList.get(userId).stream())
                .filter(neighbour -> !distances.containsKey(neighbour))
                .collect(Collectors.toSet());

        nextLevel.stream()
                .forEach(userId -> distances.put(userId, nextDistance));

        if (!nextLevel.isEmpty()) {
            bfsNextLevel(nextLevel, distances, nextDistance + 1);
        }
    }


    /**
     * Calculates the diameter of a connected component.
     *
     * @param community the users in the component
     * @return the largest shortest-path distance between its users
     */
    private int diameter(Set<Long> community) {
        return community.stream()
                .flatMap(userId -> bfs(userId).values().stream())
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
    }
}
