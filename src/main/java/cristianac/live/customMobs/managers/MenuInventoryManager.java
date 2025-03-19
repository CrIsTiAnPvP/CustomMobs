package cristianac.live.customMobs.managers;

import cristianac.live.customMobs.CustomMobs;
import cristianac.live.customMobs.models.InventorySection;
import cristianac.live.customMobs.models.PlayerInventory;
import cristianac.live.customMobs.utils.MessageGetter;
import cristianac.live.customMobs.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cristianac.live.customMobs.CustomMobs.prefix;

public class MenuInventoryManager {

    private ArrayList<PlayerInventory> players;

    private CustomMobs plugin;
    public MessageGetter getter;

    public MenuInventoryManager(CustomMobs plugin) {
        this.plugin = plugin;
        this.players = new ArrayList<>();
        this.getter = new MessageGetter(plugin);
    }

    public PlayerInventory getInventoryPlayer(Player player){
        for(PlayerInventory inventoryPlayer : players){
            if(inventoryPlayer.getPlayer().equals(player)){
                return inventoryPlayer;
            }
        }
        return null;
    }

    public void removePlayer(Player player){
        players.removeIf(inventoryPlayer -> inventoryPlayer.getPlayer().equals(player));
    }

    public void openMainInventory(PlayerInventory inventoryPlayer){
        inventoryPlayer.setSection(InventorySection.MENU_MAIN);
        Player player = inventoryPlayer.getPlayer();
        Inventory inv = Bukkit.createInventory(null,54, MessageUtils.consolemsg.deserialize(prefix + getter.getMessage("preview_menu_title")));

        // Items decorativos
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(MessageUtils.consolemsg.deserialize(""));
        item.setItemMeta(meta);
        for (int i = 0; i <= 9; i++) {
            inv.setItem(i, item);
        }
        inv.setItem(9, item);
        inv.setItem(18, item);
        inv.setItem(27, item);
        inv.setItem(36, item);
        inv.setItem(17, item);
        inv.setItem(26, item);
        inv.setItem(35, item);
        inv.setItem(44, item);
        for(int i=45;i<=53;i++){
            inv.setItem(i,item);
        }

        // Buscamos todos los mobs y ponemos como item su cabeza:
        plugin.getMobsManager().getAllMobs().forEach(mob -> {
            // Buscamos si el item de la cabeza del mob es una cabeza de jugador y la ponemos en el inventario
            List<Map<String, Object>> inventory = plugin.getMobsManager().ListInventory(mob.get("name").toString());
            if (!inventory.isEmpty()) {
                Map<String, Object>[] head = new Map[]{null};

                inventory.forEach(inventoryItem -> {
                    if (inventoryItem.get("slot").toString().equalsIgnoreCase("helmet")) {
                        head[0] = inventoryItem;
                    }
                });

                if (head[0] != null && head[0].get("item").toString().equalsIgnoreCase("PLAYER_HEAD")) {
                    ItemStack mobHead = new ItemStack(Material.PLAYER_HEAD);
                    ItemMeta mobHeadMeta = mobHead.getItemMeta();
                    mobHeadMeta.displayName(MessageUtils.consolemsg.deserialize(mob.get("name").toString()));
                    mobHead.setItemMeta(mobHeadMeta);
                    SkullMeta skullMeta = (SkullMeta) mobHead.getItemMeta();

                    if (head[0].get("head_owner").toString() != null) {
                        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(head[0].get("head_owner").toString()));
                    } else {
                        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer("CrIsTiiAn_PvP"));
                    }

                    inv.addItem(mobHead);
                } else {
                    if (mob.get("mob_type").toString().equalsIgnoreCase("Zombie")){
                        ItemStack mobHead = new ItemStack(Material.ZOMBIE_HEAD);
                        ItemMeta mobHeadMeta = mobHead.getItemMeta();
                        mobHeadMeta.displayName(MessageUtils.consolemsg.deserialize(mob.get("name").toString()));
                        mobHead.setItemMeta(mobHeadMeta);
                        inv.addItem(mobHead);
                    } else if (mob.get("mob_type").toString().equalsIgnoreCase("Skeleton")){
                        ItemStack mobHead = new ItemStack(Material.SKELETON_SKULL);
                        ItemMeta mobHeadMeta = mobHead.getItemMeta();
                        mobHeadMeta.displayName(MessageUtils.consolemsg.deserialize(mob.get("name").toString()));
                        mobHead.setItemMeta(mobHeadMeta);
                        inv.addItem(mobHead);
                    } else {
                        ItemStack mobHead = new ItemStack(Material.ENDER_EYE);
                        ItemMeta mobHeadMeta = mobHead.getItemMeta();
                        mobHeadMeta.displayName(MessageUtils.consolemsg.deserialize(mob.get("name").toString()));
                        mobHead.setItemMeta(mobHeadMeta);
                        inv.addItem(mobHead);
                    }
                }

            }
        });

        player.openInventory(inv);
        players.add(inventoryPlayer);
    }


    public void inventoryClick(PlayerInventory inventoryPlayer, int slot, ClickType clickType){
        Player player = inventoryPlayer.getPlayer();
        InventorySection section = inventoryPlayer.getSection();
       /* if(section.equals(InventorySection.MENU_MAIN)){
            if(slot == 24){
                if(!player.hasPermission("miplugin.inventario.efectos")){
                    player.sendMessage(MessageUtils.getColoredMessage(MiPlugin2.prefix+"&cSolo los VIPs pueden acceder a este menu."));
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 0.1F);
                    return;
                }
                openEffectsInventory(inventoryPlayer);
            }
        }else if(section.equals(InventorySection.MENU_EFFECTS)){
            if(slot == 18){
                openMainInventory(inventoryPlayer);
            }else if(slot == 11){
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,200,0,false,true,true));
                player.sendMessage(MessageUtils.getColoredMessage(MiPlugin2.prefix+"&aHas recibido el efecto de poción de salto."));

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2.0F);
            }else if(slot == 15){
                player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,600,2,false,true,true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,600,2,false,true,true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION,600,0,false,true,true));
                player.sendMessage(MessageUtils.getColoredMessage(MiPlugin2.prefix+"&aHas recibido el efecto de poción poderoso."));

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.1F, 2.0F);
            }
        }*/
    }
}