package com.osiki.integrationTesting.service;




import com.osiki.integrationTesting.entity.User;
import com.osiki.integrationTesting.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class) // Enables Spring testing in JUnit 5
@DataJpaTest // Loads database-related components for testing
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private UserService userService;

    // Runs before each test case to set up necessary configurations
    @BeforeEach
    void setUp() {
        userService = new UserService();
        userService.userRepository = userRepository;
    }

    @Test
    void testRegisterUser() {
        User user = userService.registerUser("John Doe", "john@example.com");

        List<User> users = userRepository.findAll();

        // Verify the user was saved to DB -- this is where we do the integration between the user service class and the database
        assertThat(user).isNotNull();
        assertThat(user.getId()).isNotNull();
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("John Doe");
        assertThat(user.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void testGetAllUsers() {
        userService.registerUser("Alice", "alice@example.com");
        userService.registerUser("Bob", "bob@example.com");

        List<User> users = userService.getAllUsers();

        // Verify the correct number of users were retrieved -- change it to 3 to fail
        assertThat(users).hasSize(2);
    }

    @Test
    void testGetUserByEmail() {
        userService.registerUser("Charlie", "charlie@example.com");

        User foundUser = userService.getUserByEmail("charlie@example.com");

        // Verify the correct user was retrieved
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getName()).isEqualTo("Charlie");
    }
}