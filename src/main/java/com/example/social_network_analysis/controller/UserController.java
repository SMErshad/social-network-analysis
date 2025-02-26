package com.example.social_network_analysis.controller;

import com.example.social_network_analysis.dto.ShortestPathResponse;
import com.example.social_network_analysis.model.User;
import com.example.social_network_analysis.service.SocialNetworkService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
    private final SocialNetworkService socialNetworkService;

    public UserController(SocialNetworkService socialNetworkService) {
        this.socialNetworkService = socialNetworkService;
    }

    @PostMapping("/users")
    public String addUser(@RequestBody User user) {
        return socialNetworkService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public String removeUser(@PathVariable String id) {
        return socialNetworkService.removeUser(id);
    }

    @GetMapping("/users")
    public Collection<User> listUsers() {
        return socialNetworkService.listUsers();
    }

    @PostMapping("/friendships")
    public String createFriendship(@RequestParam String userId1, @RequestParam String userId2) {
        return socialNetworkService.createFriendship(userId1, userId2);
    }

    @DeleteMapping("/friendships")
    public String removeFriendship(@RequestParam String userId1, @RequestParam String userId2) {
        return socialNetworkService.removeFriendship(userId1, userId2);
    }

    @GetMapping("/friends/{id}")
    public Set<String> listFriends(@PathVariable String id) {
        return socialNetworkService.listFriends(id);
    }

    @GetMapping("/shortest-path")
    public ShortestPathResponse findShortestPath(@RequestParam String userId1, @RequestParam String userId2) {
        return new ShortestPathResponse(socialNetworkService.findShortestPath(userId1, userId2));
    }
}

