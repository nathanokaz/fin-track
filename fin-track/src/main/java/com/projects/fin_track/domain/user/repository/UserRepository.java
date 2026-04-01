package com.projects.fin_track.domain.user.repository;

import com.projects.fin_track.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Integer> {

    UserDetails findByEmail(String username);
}
