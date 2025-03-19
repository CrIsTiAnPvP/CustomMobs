package cristianac.live.customMobs.managers;

import cristianac.live.customMobs.CustomMobs;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigurationMessage {

    private CustomMobs plugin;
    private String fileName;
    private FileConfiguration messageList = null;
    private File file = null;
    private String folderName;
    private boolean newFile;

    public ConfigurationMessage(String fileName, String folderName, CustomMobs plugin, boolean newFile){
        this.fileName = fileName;
        this.folderName = folderName;
        this.plugin = plugin;
        this.newFile = newFile;
    }

    public void registerMessages(){
        if(folderName != null){
            file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
        }else{
            file = new File(plugin.getDataFolder(), fileName);
        }

        if(!file.exists()){
            if(newFile){
                try{
                    file.createNewFile();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }else{
                if(folderName != null){
                    plugin.saveResource(folderName + File.separator + fileName, false);
                }else{
                    plugin.saveResource(fileName, false);
                }
            }
        }

        messageList = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getMessages() {
        if (messageList == null) {
            reloadMessages();
        }
        return messageList;
    }

    public void reloadMessages() {
        if (file == null) {
            if(folderName != null){
                file = new File(plugin.getDataFolder() + File.separator + folderName, fileName);
            }else{
                file = new File(plugin.getDataFolder(), fileName);
            }
        }
        messageList = YamlConfiguration.loadConfiguration(file);

        if(file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
            messageList.setDefaults(defConfig);
        }
    }
}