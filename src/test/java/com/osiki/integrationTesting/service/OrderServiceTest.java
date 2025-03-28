package com.osiki.integrationTesting.service;

import com.osiki.integrationTesting.entity.Order;
import com.osiki.integrationTesting.entity.User;
import com.osiki.integrationTesting.repository.OrderRepository;
import com.osiki.integrationTesting.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class OrderServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    private OrderService orderService;
    private UserService userService;
    private User testUser;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        orderService = new OrderService();

        // Inject repositories manually
        userService.userRepository = userRepository;
        orderService.orderRepository = orderRepository;
        orderService.userRepository = userRepository;

        // Create and save a test user
        testUser = userService.registerUser("Alice", "alice@example.com");
    }

    @Test
    void testPlaceOrder() {
        Order order = orderService.placeOrder("Laptop", testUser.getId());

        // Verify the order was saved
        assertThat(order).isNotNull();
        assertThat(order.getId()).isNotNull();
        assertThat(order.getUser().getId()).isEqualTo(testUser.getId());
        assertThat(order.getProductName()).isEqualTo("Laptop");
    }

    @Test
    void testGetOrdersByUser() {
        orderService.placeOrder("Laptop", testUser.getId());
        orderService.placeOrder("Phone", testUser.getId());

        List<Order> orders = orderService.getOrdersByUser(testUser.getId());

        // Verify two orders were retrieved
        assertThat(orders).hasSize(2);
    }
}

/**
 * Explanation of Integration Test
 * ✅ Test 1: testPlaceOrder()
 *
 * Registers a user.
 *
 * Places an order ("Laptop").
 *
 * Asserts that:
 *
 * The order was successfully saved.
 *
 * The order is linked to the correct user.
 *
 * The product name is stored correctly.
 *
 * ✅ Test 2: testGetOrdersByUser()
 *
 * Registers a user.
 *
 * Places two orders ("Laptop" and "Phone").
 *
 * Fetches orders for the user.
 *
 * Asserts that two orders exist.
 */