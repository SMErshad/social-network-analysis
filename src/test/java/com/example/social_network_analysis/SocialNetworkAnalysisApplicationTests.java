package com.example.social_network_analysis;

import com.example.social_network_analysis.model.User;
import com.example.social_network_analysis.service.SocialNetworkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

@SpringBootTest
class SocialNetworkAnalysisApplicationTests {

		@InjectMocks
		private SocialNetworkService socialNetworkService;

		@Mock
		private Map<String, User> users;

		@Mock
		private Map<String, Set<String>> friendships;

		@BeforeEach
		void setUp() {
			MockitoAnnotations.openMocks(this);
			socialNetworkService = new SocialNetworkService();
		}

		@Test
		void testAddUser() {
			User user = new User();
			user.setId("1");
			user.setName("John Doe");
			user.setEmail("john@example.com");
			String result = socialNetworkService.addUser(user);
			assertEquals("User added successfully!", result);
			assertTrue(socialNetworkService.listUsers().contains(user));
		}

		@Test
		void testRemoveUser() {
			User user = new User();
			user.setId("1");
			socialNetworkService.addUser(user);
			String result = socialNetworkService.removeUser("1");
			assertEquals("User removed successfully!", result);
			assertFalse(socialNetworkService.listUsers().contains(user));
		}

		@Test
		void testCreateFriendship() {
			User user1 = new User();
			user1.setId("1");
			User user2 = new User();
			user2.setId("2");
			socialNetworkService.addUser(user1);
			socialNetworkService.addUser(user2);
			String result = socialNetworkService.createFriendship("1", "2");
			assertEquals("Friendship created successfully!", result);
			assertTrue(socialNetworkService.listFriends("1").contains("2"));
			assertTrue(socialNetworkService.listFriends("2").contains("1"));
		}

		@Test
		void testRemoveFriendship() {
			User user1 = new User();
			user1.setId("1");
			User user2 = new User();
			user2.setId("2");
			socialNetworkService.addUser(user1);
			socialNetworkService.addUser(user2);
			socialNetworkService.createFriendship("1", "2");
			String result = socialNetworkService.removeFriendship("1", "2");
			assertEquals("Friendship removed successfully!", result);
			assertFalse(socialNetworkService.listFriends("1").contains("2"));
			assertFalse(socialNetworkService.listFriends("2").contains("1"));
		}

		@Test
		void testFindShortestPath() {
			User user1 = new User(); user1.setId("1");
			User user2 = new User(); user2.setId("2");
			User user3 = new User(); user3.setId("3");
			socialNetworkService.addUser(user1);
			socialNetworkService.addUser(user2);
			socialNetworkService.addUser(user3);
			socialNetworkService.createFriendship("1", "2");
			socialNetworkService.createFriendship("2", "3");
			List<String> path = socialNetworkService.findShortestPath("1", "3");
			assertEquals(Arrays.asList("1", "2", "3"), path);
		}

		@Test
		void testCalculateDegreeCentrality() {
			User user1 = new User(); user1.setId("1");
			User user2 = new User(); user2.setId("2");
			socialNetworkService.addUser(user1);
			socialNetworkService.addUser(user2);
			socialNetworkService.createFriendship("1", "2");
			Map<String, Integer> centrality = socialNetworkService.calculateDegreeCentrality();
			assertEquals(1, centrality.get("1"));
			assertEquals(1, centrality.get("2"));
		}

		@Test
		void testIdentifyCommunities() {
			User user1 = new User(); user1.setId("1");
			User user2 = new User(); user2.setId("2");
			User user3 = new User(); user3.setId("3");
			socialNetworkService.addUser(user1);
			socialNetworkService.addUser(user2);
			socialNetworkService.addUser(user3);
			socialNetworkService.createFriendship("1", "2");
			List<Set<String>> communities = socialNetworkService.identifyCommunities();
			assertEquals(2, communities.size()); // Two separate communities
		}
}
