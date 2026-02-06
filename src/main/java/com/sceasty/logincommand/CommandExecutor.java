package com.sceasty.logincommand;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    private final JavaPlugin plugin;
    private final ConfigManager configManager;
    private final MessagesManager messagesManager;
    private final PlayerDataManager playerDataManager;
    
    public CommandExecutor(JavaPlugin plugin, ConfigManager configManager, MessagesManager messagesManager, PlayerDataManager playerDataManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.messagesManager = messagesManager;
        this.playerDataManager = playerDataManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("logincommand.admin")) {
            sender.sendMessage(messagesManager.getMessage("no-permission"));
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "add":
                return handleAdd(sender, args);
            case "addfirst":
                return handleAddFirst(sender, args);
            case "remove":
                return handleRemove(sender, args);
            case "removefirst":
                return handleRemoveFirst(sender, args);
            case "list":
                return handleList(sender);
            case "listfirst":
                return handleListFirst(sender);
            case "toggle":
                return handleToggle(sender, args);
            case "reload":
                return handleReload(sender);
            case "resetplayers":
                return handleResetPlayers(sender);
            default:
                sendHelp(sender);
                return true;
        }
    }
    
    private boolean handleAdd(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(messagesManager.getMessage("usage-add"));
            sender.sendMessage(messagesManager.getMessage("example-add"));
            return true;
        }
        
        String cmd = args[1];
        boolean console = true;
        int delay = 0;
        
        if (args.length > 2) {
            console = !args[2].equalsIgnoreCase("player");
        }
        
        if (args.length > 3) {
            try {
                delay = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(messagesManager.getMessage("invalid-delay"));
                return true;
            }
        }
        
        configManager.addCommand(cmd, console, delay);
        sender.sendMessage(messagesManager.getMessage("command-added"));
        sender.sendMessage(messagesManager.getMessage("command-details", 
            "{command}", cmd, 
            "{type}", console ? "Console" : "Player", 
            "{delay}", String.valueOf(delay)));
        
        return true;
    }
    
    private boolean handleAddFirst(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(messagesManager.getMessage("usage-addfirst"));
            sender.sendMessage(messagesManager.getMessage("example-addfirst"));
            return true;
        }
        
        String cmd = args[1];
        boolean console = true;
        int delay = 0;
        
        if (args.length > 2) {
            console = !args[2].equalsIgnoreCase("player");
        }
        
        if (args.length > 3) {
            try {
                delay = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                sender.sendMessage(messagesManager.getMessage("invalid-delay"));
                return true;
            }
        }
        
        configManager.addFirstTimeCommand(cmd, console, delay);
        sender.sendMessage(messagesManager.getMessage("first-command-added"));
        sender.sendMessage(messagesManager.getMessage("command-details", 
            "{command}", cmd, 
            "{type}", console ? "Console" : "Player", 
            "{delay}", String.valueOf(delay)));
        
        return true;
    }
    
    private boolean handleRemove(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(messagesManager.getMessage("usage-remove"));
            return true;
        }
        
        String command = args[1];
        
        if (!configManager.commandExists(command)) {
            sender.sendMessage(messagesManager.getMessage("command-not-found"));
            return true;
        }
        
        configManager.removeCommand(command);
        sender.sendMessage(messagesManager.getMessage("command-removed"));
        
        return true;
    }
    
    private boolean handleRemoveFirst(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(messagesManager.getMessage("usage-removefirst"));
            return true;
        }
        
        String command = args[1];
        
        if (!configManager.firstTimeCommandExists(command)) {
            sender.sendMessage(messagesManager.getMessage("first-command-not-found"));
            return true;
        }
        
        configManager.removeFirstTimeCommand(command);
        sender.sendMessage(messagesManager.getMessage("first-command-removed"));
        
        return true;
    }
    
    private boolean handleList(CommandSender sender) {
        List<CommandData> commands = configManager.getCommands();
        
        if (commands.isEmpty()) {
            sender.sendMessage(messagesManager.getMessage("no-commands"));
            return true;
        }
        
        sender.sendMessage(messagesManager.getMessage("commands-header"));
        sender.sendMessage(configManager.isCommandsEnabled() ? 
            messagesManager.getMessage("commands-enabled") : 
            messagesManager.getMessage("commands-disabled"));
        
        int index = 1;
        for (CommandData commandData : commands) {
            sender.sendMessage(messagesManager.getMessage("command-list-item", 
                "{index}", String.valueOf(index), 
                "{command}", commandData.getCommand()));
            sender.sendMessage(messagesManager.getMessage("command-list-details", 
                "{type}", commandData.isConsoleCommand() ? "Console" : "Player", 
                "{delay}", String.valueOf(commandData.getDelay())));
            index++;
        }
        
        return true;
    }
    
    private boolean handleListFirst(CommandSender sender) {
        List<CommandData> commands = configManager.getFirstTimeCommands();
        
        if (commands.isEmpty()) {
            sender.sendMessage(messagesManager.getMessage("no-first-commands"));
            return true;
        }
        
        sender.sendMessage(messagesManager.getMessage("first-commands-header"));
        sender.sendMessage(configManager.isFirstTimeCommandsEnabled() ? 
            messagesManager.getMessage("first-commands-enabled") : 
            messagesManager.getMessage("first-commands-disabled"));
        
        int index = 1;
        for (CommandData commandData : commands) {
            sender.sendMessage(messagesManager.getMessage("command-list-item", 
                "{index}", String.valueOf(index), 
                "{command}", commandData.getCommand()));
            sender.sendMessage(messagesManager.getMessage("command-list-details", 
                "{type}", commandData.isConsoleCommand() ? "Console" : "Player", 
                "{delay}", String.valueOf(commandData.getDelay())));
            index++;
        }
        
        return true;
    }
    
    private boolean handleToggle(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(messagesManager.getMessage("usage-toggle"));
            return true;
        }
        
        String feature = args[1].toLowerCase();
        
        switch (feature) {
            case "commands":
                configManager.toggleCommands();
                sender.sendMessage(configManager.isCommandsEnabled() ? 
                    messagesManager.getMessage("commands-enabled") : 
                    messagesManager.getMessage("commands-disabled"));
                break;
            case "firstcommands":
                configManager.toggleFirstTimeCommands();
                sender.sendMessage(configManager.isFirstTimeCommandsEnabled() ? 
                    messagesManager.getMessage("first-commands-enabled") : 
                    messagesManager.getMessage("first-commands-disabled"));
                break;
            default:
                sender.sendMessage(messagesManager.getMessage("usage-toggle"));
                break;
        }
        
        return true;
    }
    
    private boolean handleReload(CommandSender sender) {
        configManager.reloadConfig();
        messagesManager.reloadMessages();
        playerDataManager.reloadData();
        sender.sendMessage(messagesManager.getMessage("plugin-reloaded"));
        return true;
    }
    
    private boolean handleResetPlayers(CommandSender sender) {
        int count = playerDataManager.getPlayerCount();
        playerDataManager.clearAllPlayers();
        sender.sendMessage(messagesManager.getMessage("players-reset"));
        sender.sendMessage(messagesManager.getMessage("player-count", "{count}", String.valueOf(count)));
        return true;
    }
    
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(messagesManager.getMessage("help-header"));
        sender.sendMessage(messagesManager.getMessage("help-add"));
        sender.sendMessage(messagesManager.getMessage("help-addfirst"));
        sender.sendMessage(messagesManager.getMessage("help-remove"));
        sender.sendMessage(messagesManager.getMessage("help-removefirst"));
        sender.sendMessage(messagesManager.getMessage("help-list"));
        sender.sendMessage(messagesManager.getMessage("help-listfirst"));
        sender.sendMessage(messagesManager.getMessage("help-toggle"));
        sender.sendMessage(messagesManager.getMessage("help-reload"));
        sender.sendMessage(messagesManager.getMessage("help-resetplayers"));
        sender.sendMessage(messagesManager.getMessage("help-placeholders"));
    }
}