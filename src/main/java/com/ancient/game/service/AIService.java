package com.ancient.game.service;

import com.ancient.game.entity.Building;
import com.ancient.game.entity.Unit;
import com.ancient.game.entity.Resource;
import com.ancient.game.repository.BuildingRepository;
import com.ancient.game.repository.UnitRepository;
import com.ancient.game.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AIService {
    @Autowired
    private BuildingRepository buildingRepository;
    
    @Autowired
    private UnitRepository unitRepository;
    
    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private GameService gameService;
    
    private Random random = new Random();
    
    public void makeAIMove(Long gameId) {
        // 获取AI资源
        Resource aiResource = resourceRepository.findByGameIdAndPlayerColor(gameId, "red");
        if (aiResource == null) return;
        
        // 获取AI建筑
        List<Building> aiBuildings = buildingRepository.findByGameIdAndPlayerColor(gameId, "red");
        
        // 获取AI单位
        List<Unit> aiUnits = unitRepository.findByGameIdAndPlayerColor(gameId, "red");
        
        // 获取玩家单位
        List<Unit> playerUnits = unitRepository.findByGameIdAndPlayerColor(gameId, "blue");
        
        // 获取玩家建筑
        List<Building> playerBuildings = buildingRepository.findByGameIdAndPlayerColor(gameId, "blue");
        
        // 1. 检查是否需要建造建筑
        if (aiResource.getWood() >= 300 && aiResource.getFood() >= 200) {
            if (aiBuildings.size() < 5) {
                int x = random.nextInt(80) + 50;
                int y = random.nextInt(80) + 50;
                gameService.createBuilding(gameId, "red", "barracks", x, y);
                gameService.updateResource(gameId, "red", -300, -200, 0, 0);
            }
        }
        
        // 2. 检查是否需要训练单位
        if (aiResource.getFood() >= 100 && aiResource.getGold() >= 50) {
            if (aiUnits.size() < 10) {
                // 找到兵营
                for (Building building : aiBuildings) {
                    if (building.getType().equals("barracks") && building.isCompleted()) {
                        gameService.createUnit(gameId, "red", "soldier", building.getX() + 1, building.getY() + 1);
                        gameService.updateResource(gameId, "red", 0, -100, -50, 0);
                        break;
                    }
                }
            }
        }
        
        // 3. 移动单位
        for (Unit unit : aiUnits) {
            if (unit.getType().equals("villager")) {
                // 村民采集资源
                if (aiResource.getWood() < 1000) {
                    // 移动到木材位置
                    int x = random.nextInt(20) + 80;
                    int y = random.nextInt(20) + 80;
                    gameService.moveUnit(unit.getId(), x, y);
                    // 增加木材
                    gameService.updateResource(gameId, "red", 10, 0, 0, 0);
                } else if (aiResource.getFood() < 1000) {
                    // 移动到食物位置
                    int x = random.nextInt(20) + 80;
                    int y = random.nextInt(20) + 80;
                    gameService.moveUnit(unit.getId(), x, y);
                    // 增加食物
                    gameService.updateResource(gameId, "red", 0, 10, 0, 0);
                }
            } else if (unit.getType().equals("soldier")) {
                // 士兵攻击玩家单位或建筑
                if (!playerUnits.isEmpty()) {
                    // 攻击玩家单位
                    Unit targetUnit = playerUnits.get(random.nextInt(playerUnits.size()));
                    gameService.moveUnit(unit.getId(), targetUnit.getX() + 1, targetUnit.getY() + 1);
                    gameService.attackUnit(unit.getId(), targetUnit.getId());
                } else if (!playerBuildings.isEmpty()) {
                    // 攻击玩家建筑
                    Building targetBuilding = playerBuildings.get(random.nextInt(playerBuildings.size()));
                    gameService.moveUnit(unit.getId(), targetBuilding.getX() + 1, targetBuilding.getY() + 1);
                    gameService.attackBuilding(unit.getId(), targetBuilding.getId());
                }
            }
        }
    }
}