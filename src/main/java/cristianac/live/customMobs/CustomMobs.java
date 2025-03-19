package cristianac.live.customMobs;

import cristianac.live.customMobs.commands.SpawnCmd;
import cristianac.live.customMobs.listeners.InventoryListener;
import cristianac.live.customMobs.managers.ConfigurationMessage;
import cristianac.live.customMobs.managers.MenuInventoryManager;
import cristianac.live.customMobs.managers.MessageManager;
import cristianac.live.customMobs.mobs.MobsManager;
import cristianac.live.customMobs.utils.MessageUtils;
import cristianac.live.customMobs.utils.TabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class CustomMobs extends JavaPlugin {

    // Version del plugin
    private String version = getPluginMeta().getVersion();

    // Prefijo del plugin
    public static String prefix = "<bold><gold>[<gradient:#5e4fa2:#f79459:red>Custom-Mobs</gradient>]</gold></bold> ";

    // Managers
    private MobsManager mobsManager;
    private MenuInventoryManager menuInventoryManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {

        // Carga de los managers
        mobsManager = new MobsManager(this);
        menuInventoryManager = new MenuInventoryManager(this);
        messageManager = new MessageManager(this);


        String linea = "------------------------------------------";

        Bukkit.getConsoleSender().sendMessage(
                MessageUtils.consolemsg.deserialize("<gradient:#5e4fa2:#f79459:red>"+ linea +"</gradient>")
        );

        Bukkit.getConsoleSender().sendMessage(" ");

        Bukkit.getConsoleSender().sendMessage(
                MessageUtils.consolemsg.deserialize(prefix + " <underlined><aqua>Plugin cargado correctamente</aqua>")
        );

        Bukkit.getConsoleSender().sendMessage(
                MessageUtils.consolemsg.deserialize(prefix + " <aqua>Versión: " + version + "</aqua>")
        );

        Bukkit.getConsoleSender().sendMessage(" ");

        Bukkit.getConsoleSender().sendMessage(
                MessageUtils.consolemsg.deserialize("<gradient:#5e4fa2:#f79459:red>"+ linea +"</gradient>")
        );

        // Carga de los comandos
        registerCommands();
        setCompleter();

        // Cargamos Eventos
        registerEvents();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(
                MessageUtils.consolemsg.deserialize(prefix + " <underlined><red>Plugin parado correctamente</red>")
        );
    }

    // Función para regristrar los comandos
    public void registerCommands() {
        this.getCommand("spawnmob").setExecutor(new SpawnCmd(this));
    }

    public void setCompleter(){
        this.getCommand("spawnmob").setTabCompleter(new TabCompleter(this));
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    // Obtención de los managers
    public MobsManager getMobsManager() {
        return mobsManager;
    }
    public MenuInventoryManager getMenuInventoryManager() {
        return menuInventoryManager;
    }
    public MessageManager getMessageManager() {
        return messageManager;
    }
}
