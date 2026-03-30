package com.ancient.game.repository;

import com.ancient.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    Game findByGameId(String gameId);
    List<Game> findByStatus(String status);
    List<Game> findByUser1IdOrUser2Id(Long userId1, Long userId2);
}