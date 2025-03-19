package cristianac.live.customMobs.mobs;

import cristianac.live.customMobs.CustomMobs;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;


public class ListMobs {

    private CustomMobs plugin;
    private String fileName;
    private FileConfiguration mobsList = null;
    private File file = null;
    private String folderName;
    private boolean newFile;

    public ListMobs(String fileName, String folderName, CustomMobs plugin, boolean newFile){
        this.fileName = fileName;
        this.folderName = folderName;
        this.plugin = plugin;
        this.newFile = newFile;
    }

    public String getPath(){
        return this.fileName;
    }

    public void registerMobs(){
        if(folderName != null){
            file = new File(plugin.getDataFolder() +File.separator + folderName,fileName);
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
                    plugin.saveResource(folderName+File.separator+fileName, false);
                }else{
                    plugin.saveResource(fileName, false);
                }
            }

        }

        mobsList = new YamlConfiguration();
        try {
            mobsList.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public void saveMobs() {
        try {
            mobsList.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getMobs() {
        if (mobsList == null) {
            reloadMobs();
        }
        return mobsList;
    }

    public boolean reloadMobs() {
        if (file == null) {
            if(folderName != null){
                file = new File(plugin.getDataFolder() +File.separator + folderName, fileName);
            }else{
                file = new File(plugin.getDataFolder(), fileName);
            }

        }
        mobsList = YamlConfiguration.loadConfiguration(file);

        if(file != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(file);
            mobsList.setDefaults(defConfig);
        }
        return true;
    }
}
