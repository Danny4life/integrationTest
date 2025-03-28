package com.osiki.integrationTesting.repository;

import com.osiki.integrationTesting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
