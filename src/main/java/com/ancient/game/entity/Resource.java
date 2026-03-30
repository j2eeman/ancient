package com.ancient.game.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "resources")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long gameId;
    private String playerColor;
    private int wood;
    private int food;
    private int gold;
    private int stone;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getGameId() {
        return gameId;
    }
    
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
    
    public String getPlayerColor() {
        return playerColor;
    }
    
    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    
    public int getWood() {
        return wood;
    }
    
    public void setWood(int wood) {
        this.wood = wood;
    }
    
    public int getFood() {
        return food;
    }
    
    public void setFood(int food) {
        this.food = food;
    }
    
    public int getGold() {
        return gold;
    }
    
    public void setGold(int gold) {
        this.gold = gold;
    }
    
    public int getStone() {
        return stone;
    }
    
    public void setStone(int stone) {
        this.stone = stone;
    }
}