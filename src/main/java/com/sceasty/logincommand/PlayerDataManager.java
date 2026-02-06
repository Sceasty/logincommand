package com.sceasty.logincommand;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerDataManager {
    private final JavaPlugin plugin;
    private File dataFile;
    private Set<String> joinedPlayers;
    
    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.joinedPlayers = new HashSet<>();
        setupDataFile();
    }
    
    private void setupDataFile() {
        dataFile = new File(plugin.getDataFolder(), "players.lc");
        
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Player data file could not be created!");
                e.printStackTrace();
            }
        }
        
        loadData();
    }
    
    private void loadData() {
        try {
            if (dataFile.length() == 0) return;
            
            String content = new String(Files.readAllBytes(dataFile.toPath()));
            String[] uuids = content.split("\n");
            
            for (String uuid : uuids) {
                if (!uuid.trim().isEmpty()) {
                    joinedPlayers.add(uuid.trim());
                }
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Player data could not be loaded!");
            e.printStackTrace();
        }
    }
    
    public boolean hasPlayerJoined(UUID playerUUID) {
        return joinedPlayers.contains(playerUUID.toString());
    }
    
    public void addPlayer(UUID playerUUID) {
        String uuidStr = playerUUID.toString();
        if (!joinedPlayers.contains(uuidStr)) {
            joinedPlayers.add(uuidStr);
            saveData();
        }
    }
    
    public void removePlayer(UUID playerUUID) {
        joinedPlayers.remove(playerUUID.toString());
        saveData();
    }
    
    public void clearAllPlayers() {
        joinedPlayers.clear();
        saveData();
    }
    
    public int getPlayerCount() {
        return joinedPlayers.size();
    }
    
    private void saveData() {
        try {
            StringBuilder content = new StringBuilder();
            for (String uuid : joinedPlayers) {
                content.append(uuid).append("\n");
            }
            
            Files.write(dataFile.toPath(), content.toString().getBytes(), 
                       StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            plugin.getLogger().severe("Player data could not be saved!");
            e.printStackTrace();
        }
    }
    
    public void reloadData() {
        joinedPlayers.clear();
        loadData();
    }
}