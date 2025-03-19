package cristianac.live.customMobs.utils;

import cristianac.live.customMobs.CustomMobs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    private CustomMobs plugin;

    public TabCompleter(CustomMobs plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> lista = new ArrayList<String>();
        if (command.getName().equalsIgnoreCase("spawnmob")) {
            if (args.length == 1) {
                lista.add("reload-mobs");
                lista.add("preview");
            }
            return lista;
        }
        return null;
    }
}
