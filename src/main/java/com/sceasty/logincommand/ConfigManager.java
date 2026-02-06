package com.sceasty.logincommand;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;
    
    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }
    
    public void loadConfig() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
    
    public List<CommandData> getCommands() {
        if (!config.getBoolean("settings.commands-enabled", true)) {
            return new ArrayList<>();
        }
        
        List<CommandData> commands = new ArrayList<>();
        
        ConfigurationSection commandsSection = config.getConfigurationSection("commands");
        if (commandsSection == null) {
            return commands;
        }
        
        Set<String> keys = commandsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(key);
            if (commandSection != null) {
                String command = commandSection.getString("command", "");
                boolean consoleCommand = commandSection.getBoolean("console", true);
                int delay = commandSection.getInt("delay", 0);
                
                commands.add(new CommandData(command, consoleCommand, delay));
            }
        }
        
        return commands;
    }
    
    public List<CommandData> getFirstTimeCommands() {
        if (!config.getBoolean("settings.first-commands-enabled", false)) {
            return new ArrayList<>();
        }
        
        List<CommandData> commands = new ArrayList<>();
        
        ConfigurationSection commandsSection = config.getConfigurationSection("first-time-commands");
        if (commandsSection == null) {
            return commands;
        }
        
        Set<String> keys = commandsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(key);
            if (commandSection != null) {
                String command = commandSection.getString("command", "");
                boolean consoleCommand = commandSection.getBoolean("console", true);
                int delay = commandSection.getInt("delay", 0);
                
                commands.add(new CommandData(command, consoleCommand, delay));
            }
        }
        
        return commands;
    }
    
    public void addCommand(String command, boolean consoleCommand, int delay) {
        String key = "cmd_" + System.currentTimeMillis();
        config.set("commands." + key + ".command", command);
        config.set("commands." + key + ".console", consoleCommand);
        config.set("commands." + key + ".delay", delay);
        plugin.saveConfig();
    }
    
    public void addFirstTimeCommand(String command, boolean consoleCommand, int delay) {
        String key = "cmd_" + System.currentTimeMillis();
        config.set("first-time-commands." + key + ".command", command);
        config.set("first-time-commands." + key + ".console", consoleCommand);
        config.set("first-time-commands." + key + ".delay", delay);
        plugin.saveConfig();
    }
    
    public void removeCommand(String command) {
        ConfigurationSection commandsSection = config.getConfigurationSection("commands");
        if (commandsSection == null) return;
        
        Set<String> keys = commandsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(key);
            if (commandSection != null) {
                String cmd = commandSection.getString("command", "");
                if (cmd.equals(command)) {
                    config.set("commands." + key, null);
                    plugin.saveConfig();
                    return;
                }
            }
        }
    }
    
    public void removeFirstTimeCommand(String command) {
        ConfigurationSection commandsSection = config.getConfigurationSection("first-time-commands");
        if (commandsSection == null) return;
        
        Set<String> keys = commandsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(key);
            if (commandSection != null) {
                String cmd = commandSection.getString("command", "");
                if (cmd.equals(command)) {
                    config.set("first-time-commands." + key, null);
                    plugin.saveConfig();
                    return;
                }
            }
        }
    }
    
    public boolean commandExists(String command) {
        ConfigurationSection commandsSection = config.getConfigurationSection("commands");
        if (commandsSection == null) return false;
        
        Set<String> keys = commandsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(key);
            if (commandSection != null) {
                String cmd = commandSection.getString("command", "");
                if (cmd.equals(command)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean firstTimeCommandExists(String command) {
        ConfigurationSection commandsSection = config.getConfigurationSection("first-time-commands");
        if (commandsSection == null) return false;
        
        Set<String> keys = commandsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection commandSection = commandsSection.getConfigurationSection(key);
            if (commandSection != null) {
                String cmd = commandSection.getString("command", "");
                if (cmd.equals(command)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void toggleCommands() {
        boolean current = config.getBoolean("settings.commands-enabled", true);
        config.set("settings.commands-enabled", !current);
        plugin.saveConfig();
    }
    
    public void toggleFirstTimeCommands() {
        boolean current = config.getBoolean("settings.first-commands-enabled", false);
        config.set("settings.first-commands-enabled", !current);
        plugin.saveConfig();
    }
    
    public boolean isCommandsEnabled() {
        return config.getBoolean("settings.commands-enabled", true);
    }
    
    public boolean isFirstTimeCommandsEnabled() {
        return config.getBoolean("settings.first-commands-enabled", false);
    }
    
    public String getLanguage() {
        return config.getString("settings.language", "en");
    }
    
    public void reloadConfig() {
        loadConfig();
    }
}