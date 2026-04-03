package com.projects.fin_track.domain.user.repository;

import com.projects.fin_track.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByEmail(String username);

    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailUser(@Param("email") String email);

}
