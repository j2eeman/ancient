package com.ancient.game.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameWebSocketHandler extends TextWebSocketHandler {
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Map<String, String> gameIdToSessionId = new ConcurrentHashMap<>();
    
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("WebSocket connection established: " + sessionId);
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);
        
        // 解析消息
        // 格式: {"action": "join", "gameId": "...", "userId": "..."}
        // 这里简化处理，实际应该使用JSON解析
        
        // 示例：加入游戏
        if (payload.contains("join")) {
            // 简化处理，实际应该使用JSON解析
            String gameId = "test-game-id";
            gameIdToSessionId.put(gameId, session.getId());
            broadcastToGame(gameId, "Player joined game: " + gameId);
        }
        
        // 示例：移动单位
        if (payload.contains("move")) {
            // 简化处理，实际应该使用JSON解析
            String gameId = "test-game-id";
            broadcastToGame(gameId, payload);
        }
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);
        
        // 清理游戏关联
        gameIdToSessionId.entrySet().removeIf(entry -> entry.getValue().equals(sessionId));
        
        System.out.println("WebSocket connection closed: " + sessionId);
    }
    
    public void broadcastToGame(String gameId, String message) {
        String sessionId = gameIdToSessionId.get(gameId);
        if (sessionId != null) {
            WebSocketSession session = sessions.get(sessionId);
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void broadcastToAll(String message) {
        for (WebSocketSession session : sessions.values()) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}