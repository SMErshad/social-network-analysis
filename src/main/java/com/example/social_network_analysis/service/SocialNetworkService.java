package com.example.social_network_analysis.service;

import com.example.social_network_analysis.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SocialNetworkService {
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, Set<String>> friendships = new HashMap<>();

    public String addUser(User user) {
        if (users.containsKey(user.getId())) {
            return "User already exists!";
        }
        users.put(user.getId(), user);
        friendships.put(user.getId(), new HashSet<>());
        return "User added successfully!";
    }

    public String removeUser(String userId) {
        if (!users.containsKey(userId)) {
            return "User not found!";
        }
        users.remove(userId);
        friendships.remove(userId);
        friendships.values().forEach(friends -> friends.remove(userId));
        return "User removed successfully!";
    }

    public Collection<User> listUsers() {
        return users.values();
    }

    public String createFriendship(String userId1, String userId2) {
        if (!users.containsKey(userId1) || !users.containsKey(userId2)) {
            return "One or both users not found!";
        }
        friendships.get(userId1).add(userId2);
        friendships.get(userId2).add(userId1);
        return "Friendship created successfully!";
    }

    public String removeFriendship(String userId1, String userId2) {
        if (!friendships.containsKey(userId1) || !friendships.containsKey(userId2)) {
            return "One or both users not found!";
        }
        friendships.get(userId1).remove(userId2);
        friendships.get(userId2).remove(userId1);
        return "Friendship removed successfully!";
    }

    public Set<String> listFriends(String userId) {
        return friendships.getOrDefault(userId, Collections.emptySet());
    }

    public List<String> findShortestPath(String start, String end) {
        if (!users.containsKey(start) || !users.containsKey(end)) return Collections.emptyList();

        Queue<List<String>> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(Collections.singletonList(start));

        while (!queue.isEmpty()) {
            List<String> path = queue.poll();
            String last = path.get(path.size() - 1);
            if (last.equals(end)) return path;

            if (!visited.contains(last)) {
                visited.add(last);
                for (String friend : friendships.getOrDefault(last, Collections.emptySet())) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(friend);
                    queue.add(newPath);
                }
            }
        }
        return Collections.emptyList();
    }

    public Map<String, Integer> calculateDegreeCentrality() {
        Map<String, Integer> centrality = new HashMap<>();
        for (String user : friendships.keySet()) {
            centrality.put(user, friendships.get(user).size());
        }
        return centrality;
    }

    public List<Set<String>> identifyCommunities() {
        List<Set<String>> communities = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        for (String user : users.keySet()) {
            if (!visited.contains(user)) {
                Set<String> community = new HashSet<>();
                exploreCommunity(user, community, visited);
                communities.add(community);
            }
        }
        return communities;
    }

    private void exploreCommunity(String user, Set<String> community, Set<String> visited) {
        Queue<String> queue = new LinkedList<>();
        queue.add(user);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            if (!visited.contains(current)) {
                visited.add(current);
                community.add(current);
                queue.addAll(friendships.getOrDefault(current, Collections.emptySet()));
            }
        }
    }
}

