package com.example.application.repository;

import com.example.application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByNameContainingIgnoreCase(String name);
}
