package com.jainam.Kimonime.repository;

import com.jainam.Kimonime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
