
package service;

import domain.Friendship;
import domain.User;

import java.util.*;
//lista ad
public class NetworkGraph {
    private final Map<Long, Set<Long>> adjacencyList = new HashMap<>();

    public NetworkGraph(Collection<User> users, Collection<Friendship> friendships) {

        // users = nod
        for (User user : users) {
            adjacencyList.put(user.getId(), new HashSet<>());
        }

        // friendships are undirected
        for (Friendship friendship : friendships) {
            Long id1 = friendship.getUserId1();
            Long id2 = friendship.getUserId2();

            adjacencyList.get(id1).add(id2);
            adjacencyList.get(id2).add(id1);
        }
    }

    public int countCommunities() {
        Set<Long> visited = new HashSet<>();
        int count = 0;

        for (Long userId : adjacencyList.keySet()) {
            if (!visited.contains(userId)) {
                count++;
                dfs(userId, visited);
            }
        }

        return count;
    }

    private void dfs(Long userId, Set<Long> visited) {
        visited.add(userId);

        for (Long neighbour : adjacencyList.get(userId)) {
            if (!visited.contains(neighbour)) {
                dfs(neighbour, visited);
            }
        }
    }

    /**
     * Finds the connected component with the largest diameter.
     * The diameter is calculated using breadth-first search.
     *
     * @return the user IDs of the selected component,
     *         or an empty set if the graph contains no vertices
     */
    public Set<Long> mostSociableCommunity() {
        Set<Long> visited = new HashSet<>();
        Set<Long> bestCommunity = new HashSet<>();
        int bestDiameter = -1;

        for (Long userId : adjacencyList.keySet()) {
            if (visited.contains(userId)) {
                continue;
            }

            Set<Long> community = new HashSet<>();
            collectCommunity(userId, visited, community);

            int diameter = 0;

            for (Long member : community) {
                Map<Long, Integer> distances = bfs(member);

                for (int distance : distances.values()) {
                    diameter = Math.max(diameter, distance);
                }
            }

            if (diameter > bestDiameter) {
                bestDiameter = diameter;
                bestCommunity = community;
            }
        }

        return bestCommunity;
    }

    private void collectCommunity(Long userId, Set<Long> visited, Set<Long> community) {
        visited.add(userId);
        community.add(userId);

        for (Long neighbour : adjacencyList.get(userId)) {
            if (!visited.contains(neighbour)) {
                collectCommunity(neighbour, visited, community);
            }
        }
    }

    private Map<Long, Integer> bfs(Long start) {
        Map<Long, Integer> distances = new HashMap<>();
        Queue<Long> queue = new LinkedList<>();

        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Long current = queue.poll();

            for (Long neighbour : adjacencyList.get(current)) {
                if (!distances.containsKey(neighbour)) {
                    distances.put(
                            neighbour,
                            distances.get(current) + 1
                    );
                    queue.add(neighbour);
                }
            }
        }

        return distances;
    }
}
