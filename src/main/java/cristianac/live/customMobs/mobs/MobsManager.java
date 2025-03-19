package cristianac.live.customMobs.mobs;

import cristianac.live.customMobs.CustomMobs;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MobsManager {

    private ListMobs listMobs;

    private CustomMobs plugin;

    private List<Map<String, Object>> all_mobs;

    public MobsManager(CustomMobs plugin) {
        this.plugin = plugin;
        listMobs = new ListMobs("mobs.yml", null, plugin, false);
        listMobs.registerMobs();
        loadMobs();
    }

    public void loadMobs() {
        FileConfiguration mobs = listMobs.getMobs();
        ConfigurationSection mobsSection = mobs.getConfigurationSection("mobs");
        all_mobs = new ArrayList<>();

        if (mobsSection != null) {
            for (String key : mobsSection.getKeys(false)) {
                Map<String, Object> mob = mobsSection.getConfigurationSection(key).getValues(false);
                mob.put("name", key);
                all_mobs.add(mob);
            }
        }
    }

    public void reloadMobs() {
        listMobs.reloadMobs();
        loadMobs();
    }

    public List<Map<String, Object>> getAllMobs() {
        return all_mobs;
    }

    public List<Map<String, Object>> ListInventory(String mobName) {
        FileConfiguration mobs = listMobs.getMobs();
        ConfigurationSection inventorySection = mobs.getConfigurationSection("mobs." + mobName + ".inventory");
        List<Map<String, Object>> inventoryList = new ArrayList<>();

        if (inventorySection != null) {
            for (String key : inventorySection.getKeys(false)) {
                Map<String, Object> item = inventorySection.getConfigurationSection(key).getValues(false);
                item.put("slot", key);
                inventoryList.add(item);
            }
        }
        return inventoryList;
    }

}
