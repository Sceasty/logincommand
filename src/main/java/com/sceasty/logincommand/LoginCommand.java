package com.sceasty.logincommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class LoginCommand extends JavaPlugin implements Listener {
    
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private MessagesManager messagesManager;
    
    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager(this);
        messagesManager = new MessagesManager(this);
        
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("logincommand").setExecutor(new CommandExecutor(this, configManager, messagesManager, playerDataManager));
        
        getLogger().info("LoginCommand plugin successfully loaded!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("LoginCommand plugin disabled!");
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        boolean isFirstTime = !playerDataManager.hasPlayerJoined(player.getUniqueId());
        
        if (isFirstTime) {
            playerDataManager.addPlayer(player.getUniqueId());
            
            List<CommandData> firstTimeCommands = configManager.getFirstTimeCommands();
            for (CommandData commandData : firstTimeCommands) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        executeCommand(player, commandData);
                    }
                }.runTaskLater(this, commandData.getDelay() * 20L);
            }
        }
        
        List<CommandData> commands = configManager.getCommands();
        
        for (CommandData commandData : commands) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    executeCommand(player, commandData);
                }
            }.runTaskLater(this, commandData.getDelay() * 20L);
        }
    }
    
    private void executeCommand(Player player, CommandData commandData) {
        String command = commandData.getCommand();
        
        command = command.replace("{player}", player.getName());
        command = command.replace("{uuid}", player.getUniqueId().toString());
        
        if (commandData.isConsoleCommand()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        } else {
            Bukkit.dispatchCommand(player, command);
        }
    }
}