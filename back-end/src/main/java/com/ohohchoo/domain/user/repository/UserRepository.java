package com.ohohchoo.domain.user.repository;

import com.ohohchoo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findById(Long id);
    public Optional<User> findByEmail(String email);
}
