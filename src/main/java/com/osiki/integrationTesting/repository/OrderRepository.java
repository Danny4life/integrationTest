package com.osiki.integrationTesting.repository;

import com.osiki.integrationTesting.entity.Order;
import com.osiki.integrationTesting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
