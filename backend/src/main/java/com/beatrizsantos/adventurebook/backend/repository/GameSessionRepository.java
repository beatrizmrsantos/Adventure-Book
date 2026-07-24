package com.beatrizsantos.adventurebook.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beatrizsantos.adventurebook.backend.model.GameSession;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, Long>{

    Optional<GameSession> findByBookId(Long bookId);
    
}
