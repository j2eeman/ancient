package com.ancient.game.controller;

import com.ancient.game.entity.Game;
import com.ancient.game.service.GameService;
import com.ancient.game.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    @Autowired
    private GameService gameService;
    
    @Autowired
    private AIService aiService;
    
    @PostMapping("/create")
    public Game createGame(@RequestParam Long userId, @RequestParam boolean isAI) {
        return gameService.createGame(userId, isAI);
    }
    
    @PostMapping("/join")
    public Game joinGame(@RequestParam Long userId, @RequestParam String gameId) {
        return gameService.joinGame(userId, gameId);
    }
    
    @GetMapping("/id/{gameId}")
    public Game getGame(@PathVariable String gameId) {
        return gameService.getGame(gameId);
    }
    
    @GetMapping("/waiting")
    public List<Game> getWaitingGames() {
        return gameService.getWaitingGames();
    }
    
    @GetMapping("/user/{userId}")
    public List<Game> getUserGames(@PathVariable Long userId) {
        return gameService.getUserGames(userId);
    }
    
    @PostMapping("/end")
    public void endGame(@RequestParam String gameId, @RequestParam String winner) {
        gameService.endGame(gameId, winner);
    }
    
    @PostMapping("/update-resource")
    public void updateResource(@RequestParam Long gameId, @RequestParam String playerColor, 
                               @RequestParam int wood, @RequestParam int food, 
                               @RequestParam int gold, @RequestParam int stone) {
        gameService.updateResource(gameId, playerColor, wood, food, gold, stone);
    }
    
    @PostMapping("/create-building")
    public void createBuilding(@RequestParam Long gameId, @RequestParam String playerColor, 
                               @RequestParam String type, @RequestParam int x, @RequestParam int y) {
        gameService.createBuilding(gameId, playerColor, type, x, y);
    }
    
    @PostMapping("/create-unit")
    public void createUnit(@RequestParam Long gameId, @RequestParam String playerColor, 
                           @RequestParam String type, @RequestParam int x, @RequestParam int y) {
        gameService.createUnit(gameId, playerColor, type, x, y);
    }
    
    @PostMapping("/move-unit")
    public void moveUnit(@RequestParam Long unitId, @RequestParam int x, @RequestParam int y) {
        gameService.moveUnit(unitId, x, y);
    }
    
    @PostMapping("/attack-unit")
    public void attackUnit(@RequestParam Long attackerId, @RequestParam Long targetId) {
        gameService.attackUnit(attackerId, targetId);
    }
    
    @PostMapping("/attack-building")
    public void attackBuilding(@RequestParam Long attackerId, @RequestParam Long buildingId) {
        gameService.attackBuilding(attackerId, buildingId);
    }
    
    @PostMapping("/ai-move")
    public void makeAIMove(@RequestParam Long gameId) {
        aiService.makeAIMove(gameId);
    }
}