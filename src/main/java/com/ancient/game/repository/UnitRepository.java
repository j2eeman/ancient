package com.ancient.game.repository;

import com.ancient.game.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findByGameId(Long gameId);
    List<Unit> findByGameIdAndPlayerColor(Long gameId, String playerColor);
}