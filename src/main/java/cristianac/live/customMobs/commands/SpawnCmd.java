package cristianac.live.customMobs.commands;

import cristianac.live.customMobs.CustomMobs;
import cristianac.live.customMobs.models.PlayerInventory;
import cristianac.live.customMobs.utils.ErrorHandler;
import cristianac.live.customMobs.utils.MessageGetter;
import cristianac.live.customMobs.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static cristianac.live.customMobs.CustomMobs.prefix;

public class SpawnCmd implements CommandExecutor {

    private CustomMobs plugin;

    public MessageGetter messageGetter;

    public SpawnCmd(CustomMobs plugin) {
        this.plugin = plugin;
        this.messageGetter = new MessageGetter(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player jugador = (Player) sender;

            if (args.length == 0) {
                jugador.sendMessage(
                        MessageUtils.usermsg.deserialize(prefix + " " + messageGetter.getMessage("no_mob_specified"))
                );
                listaMobs(jugador);
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("reload-mobs")) {
                    reloadMobs(jugador);
                } else if (args[0].equalsIgnoreCase("preview")) {
                    previewMobs(jugador);
                }
            }
        } else {
            sender.sendMessage(MessageUtils.consolemsg.deserialize(prefix + " " + messageGetter.getMessage("no_player")));
        }

        return false;
    }

    public void listaMobs(CommandSender jugador) {
        plugin.getMobsManager().getAllMobs().forEach(mob -> {
            jugador.sendMessage(MessageUtils.usermsg.deserialize(prefix + " <hover:show_text:'<aqua>"+ mob.get("mob_type").toString().toLowerCase() +"'><red>" + mob.get("name") + "</hover>"));

            List<Map<String, Object>> inventory = plugin.getMobsManager().ListInventory(mob.get("name").toString());
            if (!inventory.isEmpty()) {
                jugador.sendMessage(MessageUtils.usermsg.deserialize(prefix + " <yellow>Inventory:"));
                for (Map<String, Object> item : inventory) {
                    try {
                        jugador.sendMessage(MessageUtils.usermsg.deserialize(prefix + " <gray>" + item.toString()));
                    } catch (Exception e) {
                        ErrorHandler.handleException(e, jugador);
                    }

                }
            } else {
                jugador.sendMessage(MessageUtils.usermsg.deserialize(prefix + " <red>No inventory found."));
            }
        });

        StringBuilder allMobsString = new StringBuilder("All Mobs:\n");
        for (Map<String, Object> mob : plugin.getMobsManager().getAllMobs()) {
            allMobsString.append(mob.toString()).append("\n");
        }
        // Logs de todo mobs.yml
        Bukkit.getConsoleSender().sendMessage(allMobsString.toString());
    }

    public void reloadMobs(CommandSender jugador) {
        if (!jugador.hasPermission("custommobs.reload")) {
            jugador.sendMessage(
                    MessageUtils.usermsg.deserialize(prefix + " " + messageGetter.noPermission())
            );
        }
        plugin.getMobsManager().reloadMobs();
        jugador.sendMessage(
                MessageUtils.usermsg.deserialize(prefix + " " + messageGetter.getMessage("mobs_reload"))
        );
    }

    public void previewMobs(Player jugador) {
        plugin.getMenuInventoryManager().openMainInventory(new PlayerInventory(jugador));
    }
}
