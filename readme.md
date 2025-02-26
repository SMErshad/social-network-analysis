# Social Network Analysis Tool Documentation

## Overview
The Social Network Analysis Tool is a Spring Boot application that allows users to manage a social network and perform various analyses such as finding shortest paths between users, identifying communities, and calculating degree centrality.

## Features
- User Management: Add, remove, and list users.
- Friendship Management: Create and remove friendships, list friends of a user.
- Network Analysis: Shortest path calculation, community detection, and degree centrality calculation.

## API Endpoints

### User Management
- `POST /users/add` - Add a new user.
- `DELETE /users/remove/{id}` - Remove a user.
- `GET /users/list` - List all users.

### Friendship Management
- `POST /users/friendship/add` - Create a friendship.
- `POST /users/friendship/remove` - Remove a friendship.
- `GET /users/friends/{id}` - List friends of a user.

### Network Analysis
- `GET /users/shortest-path` - Find shortest path between two users.
- `GET /users/degree-centrality` - Calculate degree centrality.
- `GET /users/communities` - Identify communities.

## Algorithms Used
- **BFS (Breadth-First Search)** for shortest path calculation.
- **Graph Traversal** for community detection.
- **Degree Counting** for centrality calculation.

## Performance Considerations
- HashMaps for efficient user and friendship lookup.
- BFS ensures shortest path computation in O(V + E) time complexity.
- Community detection uses BFS, providing efficient clustering.

## Testing with JUnit and Mockito
### Dependencies
Add the following dependencies to your `pom.xml`:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Sample Test Cases
```java
package com.example.socialnetwork;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;

class UserControllerTest {
    @InjectMocks
    private UserController userController;
    
    @Mock
    private Map<String, User> users;
    
    @Mock
    private Map<String, Set<String>> friendships;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setEmail("john@example.com");
        
        String response = userController.addUser(user);
        assertEquals("User added successfully!", response);
    }
    
    @Test
    void testCreateFriendship() {
        when(users.containsKey("1")).thenReturn(true);
        when(users.containsKey("2")).thenReturn(true);
        when(friendships.get("1")).thenReturn(new HashSet<>());
        when(friendships.get("2")).thenReturn(new HashSet<>());
        
        String response = userController.createFriendship("1", "2");
        assertEquals("Friendship created successfully!", response);
    }
}
```

This test suite validates user addition and friendship creation using JUnit and Mockito.

## Conclusion
This documentation outlines the Social Network Analysis Tool, its API endpoints, algorithms, and testing strategies. Future improvements could include integrating a database for persistence and optimizing community detection algorithms.

