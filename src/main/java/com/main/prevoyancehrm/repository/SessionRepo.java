package com.main.prevoyancehrm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.main.prevoyancehrm.entities.Sessions;

public interface SessionRepo extends JpaRepository<Sessions,Long>{
    Optional<Sessions> findBySessionId(Long sessionId);
    Optional<Sessions> findByToken(String token);;
}
