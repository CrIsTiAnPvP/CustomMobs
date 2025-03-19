package cristianac.live.customMobs.models;

import org.bukkit.entity.Player;

public class PlayerInventory {
    private Player player;
    private InventorySection section;

    public PlayerInventory(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public InventorySection getSection() {
        return section;
    }

    public void setSection(InventorySection section) {
        this.section = section;
    }
}
