package com.ancient.game.repository;

import com.ancient.game.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    List<Building> findByGameId(Long gameId);
    List<Building> findByGameIdAndPlayerColor(Long gameId, String playerColor);
}