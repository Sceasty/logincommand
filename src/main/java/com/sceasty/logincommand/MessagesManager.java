package com.sceasty.logincommand;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class MessagesManager {
    private final JavaPlugin plugin;
    private File messagesFile;
    private FileConfiguration messagesConfig;
    private final Map<String, Map<String, String>> messages;
    
    public MessagesManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.messages = new HashMap<>();
        setupMessages();
        setupMessagesFile();
    }
    
    private void setupMessages() {
        Map<String, String> english = new HashMap<>();
        english.put("no-permission", "&cYou don't have permission to use this command!");
        english.put("plugin-reloaded", "&aConfig and messages successfully reloaded!");
        english.put("invalid-delay", "&cInvalid delay value!");
        english.put("command-added", "&aCommand successfully added!");
        english.put("first-command-added", "&aFirst time command successfully added!");
        english.put("command-details", "&7Command: {command} | Type: {type} | Delay: {delay}s");
        english.put("command-removed", "&aCommand successfully removed!");
        english.put("first-command-removed", "&aFirst time command successfully removed!");
        english.put("command-not-found", "&cNo command found with this name!");
        english.put("first-command-not-found", "&cNo first time command found with this name!");
        english.put("no-commands", "&eNo commands added yet!");
        english.put("no-first-commands", "&eNo first time commands added yet!");
        english.put("commands-header", "&a=== Login Commands ===");
        english.put("first-commands-header", "&a=== First Time Commands ===");
        english.put("command-list-item", "&e{index}. &f{command}");
        english.put("command-list-details", "&7   Type: {type} | Delay: {delay}s");
        english.put("players-reset", "&aAll player data has been reset! Everyone will be considered as first time joiners.");
        english.put("player-count", "&7Total registered players: {count}");
        english.put("commands-enabled", "&aLogin commands enabled!");
        english.put("commands-disabled", "&cLogin commands disabled!");
        english.put("first-commands-enabled", "&aFirst time commands enabled!");
        english.put("first-commands-disabled", "&cFirst time commands disabled!");
        english.put("help-header", "&a=== LoginCommand Help ===");
        english.put("help-add", "&e/lc add <command> [console/player] [delay] &f- Add normal command");
        english.put("help-addfirst", "&e/lc addfirst <command> [console/player] [delay] &f- Add first time command");
        english.put("help-remove", "&e/lc remove <command> &f- Remove normal command");
        english.put("help-removefirst", "&e/lc removefirst <command> &f- Remove first time command");
        english.put("help-list", "&e/lc list &f- List normal commands");
        english.put("help-listfirst", "&e/lc listfirst &f- List first time commands");
        english.put("help-toggle", "&e/lc toggle [commands/firstcommands] &f- Toggle features");
        english.put("help-reload", "&e/lc reload &f- Reload config and messages");
        english.put("help-resetplayers", "&e/lc resetplayers &f- Reset player data");
        english.put("help-placeholders", "&7Placeholders: {player}, {uuid}");
        english.put("usage-add", "&cUsage: /lc add <command> [console/player] [delay]");
        english.put("usage-addfirst", "&cUsage: /lc addfirst <command> [console/player] [delay]");
        english.put("usage-remove", "&cUsage: /lc remove <command>");
        english.put("usage-removefirst", "&cUsage: /lc removefirst <command>");
        english.put("usage-toggle", "&cUsage: /lc toggle [commands/firstcommands]");
        english.put("example-add", "&eExample: /lc add \"say {player} welcome to server!\" console 5");
        english.put("example-addfirst", "&eExample: /lc addfirst \"give {player} diamond 10\" console 3");
        
        Map<String, String> turkish = new HashMap<>();
        turkish.put("no-permission", "&cBu komutu kullanmak için yetkiniz yok!");
        turkish.put("plugin-reloaded", "&aConfig ve mesajlar başarıyla yeniden yüklendi!");
        turkish.put("invalid-delay", "&cGeçersiz delay değeri!");
        turkish.put("command-added", "&aKomut başarıyla eklendi!");
        turkish.put("first-command-added", "&aİlk giriş komutu başarıyla eklendi!");
        turkish.put("command-details", "&7Komut: {command} | Tip: {type} | Delay: {delay}s");
        turkish.put("command-removed", "&aKomut başarıyla silindi!");
        turkish.put("first-command-removed", "&aİlk giriş komutu başarıyla silindi!");
        turkish.put("command-not-found", "&cBu isimle bir komut bulunamadı!");
        turkish.put("first-command-not-found", "&cBu isimle bir ilk giriş komutu bulunamadı!");
        turkish.put("no-commands", "&eHenüz hiç komut eklenmemiş!");
        turkish.put("no-first-commands", "&eHenüz hiç ilk giriş komutu eklenmemiş!");
        turkish.put("commands-header", "&a=== Login Komutları ===");
        turkish.put("first-commands-header", "&a=== İlk Giriş Komutları ===");
        turkish.put("command-list-item", "&e{index}. &f{command}");
        turkish.put("command-list-details", "&7   Tip: {type} | Delay: {delay}s");
        turkish.put("players-reset", "&aTüm oyuncu verileri sıfırlandı! Artık herkes ilk defa giriyor sayılacak.");
        turkish.put("player-count", "&7Toplam kayıtlı oyuncu: {count}");
        turkish.put("commands-enabled", "&aLogin komutları aktif!");
        turkish.put("commands-disabled", "&cLogin komutları deaktif!");
        turkish.put("first-commands-enabled", "&aİlk giriş komutları aktif!");
        turkish.put("first-commands-disabled", "&cİlk giriş komutları deaktif!");
        turkish.put("help-header", "&a=== LoginCommand Yardım ===");
        turkish.put("help-add", "&e/lc add <komut> [console/player] [delay] &f- Normal komut ekle");
        turkish.put("help-addfirst", "&e/lc addfirst <komut> [console/player] [delay] &f- İlk giriş komutu ekle");
        turkish.put("help-remove", "&e/lc remove <komut> &f- Normal komut sil");
        turkish.put("help-removefirst", "&e/lc removefirst <komut> &f- İlk giriş komutu sil");
        turkish.put("help-list", "&e/lc list &f- Normal komutları listele");
        turkish.put("help-listfirst", "&e/lc listfirst &f- İlk giriş komutlarını listele");
        turkish.put("help-toggle", "&e/lc toggle [commands/firstcommands] &f- Özellikleri aç/kapat");
        turkish.put("help-reload", "&e/lc reload &f- Config ve mesajları yeniden yükle");
        turkish.put("help-resetplayers", "&e/lc resetplayers &f- Oyuncu verilerini sıfırla");
        turkish.put("help-placeholders", "&7Placeholder'lar: {player}, {uuid}");
        turkish.put("usage-add", "&cKullanım: /lc add <komut> [console/player] [delay]");
        turkish.put("usage-addfirst", "&cKullanım: /lc addfirst <komut> [console/player] [delay]");
        turkish.put("usage-remove", "&cKullanım: /lc remove <komut>");
        turkish.put("usage-removefirst", "&cKullanım: /lc removefirst <komut>");
        turkish.put("usage-toggle", "&cKullanım: /lc toggle [commands/firstcommands]");
        turkish.put("example-add", "&eÖrnek: /lc add \"say {player} sunucuya hoşgeldin!\" console 5");
        turkish.put("example-addfirst", "&eÖrnek: /lc addfirst \"give {player} diamond 10\" console 3");
        
        messages.put("en", english);
        messages.put("tr", turkish);
    }
    
    private void setupMessagesFile() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        
        if (!messagesFile.exists()) {
            try {
                InputStream defaultMessages = plugin.getResource("messages.yml");
                if (defaultMessages != null) {
                    Files.copy(defaultMessages, messagesFile.toPath());
                } else {
                    messagesFile.createNewFile();
                }
            } catch (IOException e) {
                plugin.getLogger().severe("Messages file could not be created!");
                e.printStackTrace();
            }
        }
        
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
    
    public String getMessage(String key) {
        String language = plugin.getConfig().getString("settings.language", "en");
        Map<String, String> langMessages = messages.get(language);
        
        if (langMessages == null) {
            langMessages = messages.get("en");
        }
        
        String message = langMessages.get(key);
        if (message == null) {
            message = "Message not found: " + key;
        }
        
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public String getMessage(String key, String... placeholders) {
        String message = getMessage(key);
        
        for (int i = 0; i < placeholders.length; i += 2) {
            if (i + 1 < placeholders.length) {
                message = message.replace(placeholders[i], placeholders[i + 1]);
            }
        }
        
        return message;
    }
    
    public void reloadMessages() {
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }
}