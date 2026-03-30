package com.ancient.game.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String gameId;
    private String status; // waiting, playing, finished
    private String winner;
    private Date startTime;
    private Date endTime;
    
    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;
    
    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;
    
    private String user1Color;
    private String user2Color;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getGameId() {
        return gameId;
    }
    
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getWinner() {
        return winner;
    }
    
    public void setWinner(String winner) {
        this.winner = winner;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public User getUser1() {
        return user1;
    }
    
    public void setUser1(User user1) {
        this.user1 = user1;
    }
    
    public User getUser2() {
        return user2;
    }
    
    public void setUser2(User user2) {
        this.user2 = user2;
    }
    
    public String getUser1Color() {
        return user1Color;
    }
    
    public void setUser1Color(String user1Color) {
        this.user1Color = user1Color;
    }
    
    public String getUser2Color() {
        return user2Color;
    }
    
    public void setUser2Color(String user2Color) {
        this.user2Color = user2Color;
    }
}