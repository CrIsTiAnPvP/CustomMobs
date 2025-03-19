package cristianac.live.customMobs.utils;

import cristianac.live.customMobs.CustomMobs;
import cristianac.live.customMobs.managers.MessageManager;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Map;

public class MessageGetter {

    private final CustomMobs plugin;

    private MessageGetter messageGetter;

    public MessageGetter(CustomMobs plugin){
        this.plugin = plugin;
    }

    public String getMessage(String key) {
        for (Map<String, Object> messageMap : plugin.getMessageManager().getAllMessages()) {
            if (messageMap.get("message").equals(key)) {
                return (String) messageMap.get("text");
            }
        }
        return null; // or a default message
    }

    public String noPermission() {
        return getMessage("no_permission");
    }

}
