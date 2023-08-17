package com.nissum.technical.challenge.users.repository;

import com.nissum.technical.challenge.users.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    boolean existsByEmail(String email);

    @Query("SELECT DISTINCT u FROM Users u LEFT JOIN FETCH u.phones")
    List<Users> getAllUsers();
}
