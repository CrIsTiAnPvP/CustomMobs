package cristianac.live.customMobs.managers;

import cristianac.live.customMobs.CustomMobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageManager {

    private final ConfigurationMessage configurationMessage;

    private List<Map<String, Object>> all_messages;

    public MessageManager(CustomMobs plugin) {
        configurationMessage = new ConfigurationMessage("messages.yml", null, plugin, false);
        configurationMessage.registerMessages();
        loadMessages();
    }

    public void loadMessages() {
        FileConfiguration messages = configurationMessage.getMessages();
        ConfigurationSection messageSection = messages.getConfigurationSection("messages");
        all_messages = new ArrayList<>();

        for (String key : messageSection.getKeys(false)) {
            String message = messageSection.getString(key);
            if (message != null) {
                Map<String, Object> messageMap = new HashMap<>();
                messageMap.put("message", key);
                messageMap.put("text", message);
                all_messages.add(messageMap);
            }
        }

    }

    public void reloadMessages() {
        configurationMessage.reloadMessages();
        loadMessages();
    }

    public List<Map<String, Object>> getAllMessages() {
        return all_messages;
    }
}