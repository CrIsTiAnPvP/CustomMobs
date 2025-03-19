package cristianac.live.customMobs.listeners;

import cristianac.live.customMobs.CustomMobs;
import cristianac.live.customMobs.models.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {


    private CustomMobs plugin;

    public InventoryListener(CustomMobs plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player jugador = (Player) event.getWhoClicked();
        PlayerInventory inventarioJugador = plugin.getMenuInventoryManager().getInventoryPlayer(jugador);
        if (inventarioJugador != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player jugador = (Player) event.getPlayer();
        plugin.getMenuInventoryManager().removePlayer(jugador);
    }

}
