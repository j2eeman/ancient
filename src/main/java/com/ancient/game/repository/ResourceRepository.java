package com.ancient.game.repository;

import com.ancient.game.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByGameId(Long gameId);
    Resource findByGameIdAndPlayerColor(Long gameId, String playerColor);
}