package com.ancient.game.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ancient.game.entity.Building;
import com.ancient.game.entity.Game;
import com.ancient.game.entity.Resource;
import com.ancient.game.entity.Unit;
import com.ancient.game.entity.User;
import com.ancient.game.repository.BuildingRepository;
import com.ancient.game.repository.GameRepository;
import com.ancient.game.repository.ResourceRepository;
import com.ancient.game.repository.UnitRepository;
import com.ancient.game.repository.UserRepository;

@Service
public class GameService {
    @Autowired
    private GameRepository gameRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private BuildingRepository buildingRepository;
    
    @Autowired
    private UnitRepository unitRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Game createGame(Long userId, boolean isAI) {
        Game game = new Game();
        game.setGameId(UUID.randomUUID().toString());
        game.setStatus(isAI ? "playing" : "waiting");
        game.setStartTime(new Date());
        
        // 从数据库获取用户
        User user1 = userRepository.findById(userId).orElse(null);
        if (user1 == null) {
            throw new RuntimeException("User not found");
        }
        game.setUser1(user1);
        game.setUser1Color("blue");
        
        if (isAI) {
            game.setUser2(null);
            game.setUser2Color("red");
        }
        
        game = gameRepository.save(game);
        
        // 初始化资源
        initializeResources(game.getId());
        
        // 初始化建筑
        initializeBuildings(game.getId());
        
        // 初始化单位
        initializeUnits(game.getId());
        
        return game;
    }
    
    public Game joinGame(Long userId, String gameId) {
        Game game = gameRepository.findByGameId(gameId);
        if (game == null || !game.getStatus().equals("waiting")) {
            throw new RuntimeException("Game not found or not waiting for players");
        }
        
        // 从数据库获取用户
        User user2 = userRepository.findById(userId).orElse(null);
        if (user2 == null) {
            throw new RuntimeException("User not found");
        }
        game.setUser2(user2);
        game.setUser2Color("red");
        game.setStatus("playing");
        
        return gameRepository.save(game);
    }
    
    private void initializeResources(Long gameId) {
        // 蓝方资源
        Resource blueResource = new Resource();
        blueResource.setGameId(gameId);
        blueResource.setPlayerColor("blue");
        blueResource.setWood(500);
        blueResource.setFood(500);
        blueResource.setGold(200);
        blueResource.setStone(200);
        resourceRepository.save(blueResource);
        
        // 红方资源
        Resource redResource = new Resource();
        redResource.setGameId(gameId);
        redResource.setPlayerColor("red");
        redResource.setWood(500);
        redResource.setFood(500);
        redResource.setGold(200);
        redResource.setStone(200);
        resourceRepository.save(redResource);
    }
    
    private void initializeBuildings(Long gameId) {
        // 蓝方建筑
        Building blueTownCenter = new Building();
        blueTownCenter.setGameId(gameId);
        blueTownCenter.setPlayerColor("blue");
        blueTownCenter.setType("town_center");
        blueTownCenter.setX(10);
        blueTownCenter.setY(10);
        blueTownCenter.setHealth(2000);
        blueTownCenter.setCompleted(true);
        buildingRepository.save(blueTownCenter);
        
        // 红方建筑
        Building redTownCenter = new Building();
        redTownCenter.setGameId(gameId);
        redTownCenter.setPlayerColor("red");
        redTownCenter.setType("town_center");
        redTownCenter.setX(90);
        redTownCenter.setY(90);
        redTownCenter.setHealth(2000);
        redTownCenter.setCompleted(true);
        buildingRepository.save(redTownCenter);
    }
    
    private void initializeUnits(Long gameId) {
        // 蓝方单位
        Unit blueVillager1 = new Unit();
        blueVillager1.setGameId(gameId);
        blueVillager1.setPlayerColor("blue");
        blueVillager1.setType("villager");
        blueVillager1.setX(12);
        blueVillager1.setY(10);
        blueVillager1.setHealth(100);
        blueVillager1.setAttack(5);
        blueVillager1.setDefense(0);
        blueVillager1.setSpeed(3);
        unitRepository.save(blueVillager1);
        
        Unit blueVillager2 = new Unit();
        blueVillager2.setGameId(gameId);
        blueVillager2.setPlayerColor("blue");
        blueVillager2.setType("villager");
        blueVillager2.setX(10);
        blueVillager2.setY(12);
        blueVillager2.setHealth(100);
        blueVillager2.setAttack(5);
        blueVillager2.setDefense(0);
        blueVillager2.setSpeed(3);
        unitRepository.save(blueVillager2);
        
        // 红方单位
        Unit redVillager1 = new Unit();
        redVillager1.setGameId(gameId);
        redVillager1.setPlayerColor("red");
        redVillager1.setType("villager");
        redVillager1.setX(88);
        redVillager1.setY(90);
        redVillager1.setHealth(100);
        redVillager1.setAttack(5);
        redVillager1.setDefense(0);
        redVillager1.setSpeed(3);
        unitRepository.save(redVillager1);
        
        Unit redVillager2 = new Unit();
        redVillager2.setGameId(gameId);
        redVillager2.setPlayerColor("red");
        redVillager2.setType("villager");
        redVillager2.setX(90);
        redVillager2.setY(88);
        redVillager2.setHealth(100);
        redVillager2.setAttack(5);
        redVillager2.setDefense(0);
        redVillager2.setSpeed(3);
        unitRepository.save(redVillager2);
    }
    
    public Game getGame(String gameId) {
        return gameRepository.findByGameId(gameId);
    }
    
    public List<Game> getWaitingGames() {
        return gameRepository.findByStatus("waiting");
    }
    
    public List<Game> getUserGames(Long userId) {
        return gameRepository.findByUser1IdOrUser2Id(userId, userId);
    }
    
    public void endGame(String gameId, String winner) {
        Game game = gameRepository.findByGameId(gameId);
        if (game != null) {
            game.setStatus("finished");
            game.setWinner(winner);
            game.setEndTime(new Date());
            gameRepository.save(game);
        }
    }
    
    public void updateResource(Long gameId, String playerColor, int wood, int food, int gold, int stone) {
        Resource resource = resourceRepository.findByGameIdAndPlayerColor(gameId, playerColor);
        if (resource != null) {
            resource.setWood(resource.getWood() + wood);
            resource.setFood(resource.getFood() + food);
            resource.setGold(resource.getGold() + gold);
            resource.setStone(resource.getStone() + stone);
            resourceRepository.save(resource);
        }
    }
    
    public void createBuilding(Long gameId, String playerColor, String type, int x, int y) {
        Building building = new Building();
        building.setGameId(gameId);
        building.setPlayerColor(playerColor);
        building.setType(type);
        building.setX(x);
        building.setY(y);
        building.setHealth(1000);
        building.setCompleted(false);
        buildingRepository.save(building);
    }
    
    public void createUnit(Long gameId, String playerColor, String type, int x, int y) {
        Unit unit = new Unit();
        unit.setGameId(gameId);
        unit.setPlayerColor(playerColor);
        unit.setType(type);
        unit.setX(x);
        unit.setY(y);
        unit.setHealth(100);
        unit.setAttack(10);
        unit.setDefense(0);
        unit.setSpeed(4);
        unitRepository.save(unit);
    }
    
    public void moveUnit(Long unitId, int x, int y) {
        Unit unit = unitRepository.findById(unitId).orElse(null);
        if (unit != null) {
            unit.setX(x);
            unit.setY(y);
            unitRepository.save(unit);
        }
    }
    
    public void attackUnit(Long attackerId, Long targetId) {
        Unit attacker = unitRepository.findById(attackerId).orElse(null);
        Unit target = unitRepository.findById(targetId).orElse(null);
        
        if (attacker != null && target != null) {
            int damage = attacker.getAttack() - target.getDefense();
            if (damage > 0) {
                target.setHealth(target.getHealth() - damage);
                if (target.getHealth() <= 0) {
                    unitRepository.delete(target);
                } else {
                    unitRepository.save(target);
                }
            }
        }
    }
    
    public void attackBuilding(Long attackerId, Long buildingId) {
        Unit attacker = unitRepository.findById(attackerId).orElse(null);
        Building building = buildingRepository.findById(buildingId).orElse(null);
        
        if (attacker != null && building != null) {
            int damage = attacker.getAttack();
            building.setHealth(building.getHealth() - damage);
            if (building.getHealth() <= 0) {
                buildingRepository.delete(building);
            } else {
                buildingRepository.save(building);
            }
        }
    }
}