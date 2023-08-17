package com.nissum.technical.challenge.users.repository;

import com.nissum.technical.challenge.users.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    boolean existsByEmail(String email);
}
