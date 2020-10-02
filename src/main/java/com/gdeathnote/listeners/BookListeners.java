package com.gdeathnote.listeners;

import com.gdeathnote.GDeathnote;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.metadata.FixedMetadataValue;

public class BookListeners implements Listener {

/*    public static Map<Player, ItemStack[]> items = new HashMap<>();
    public static Map<Player, ItemStack[]> armors = new HashMap<>();
    public static Map<Player, ItemStack[]> extra = new HashMap<>();*/

    @EventHandler
    public void onEditBook(PlayerEditBookEvent e) {
        if (!e.isSigning() && e.getNewBookMeta().getDisplayName().equals("§cDeathnote")) {
            String text = e.getNewBookMeta().getPage(1);
            Player target = Bukkit.getPlayer(text);
            ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
            BookMeta bookMeta = (BookMeta) item.getItemMeta();
            bookMeta.setPages("§7Digite o nome aqui...");
            bookMeta.setDisplayName("§cDeathnote");
            item.setItemMeta(bookMeta);
            if (target == null) {
                e.getPlayer().sendMessage("§cO jogador citado no livro não foi encontrado.");
            } else if (target.equals(e.getPlayer())) {
                e.getPlayer().sendMessage("§cVocê não pode matar você mesmo");
            } else {
                e.getNewBookMeta().setPage(1, "§7Digite o nome aqui...");
                e.getPreviousBookMeta().setPage(1, "§7Digite o nome aqui...");
                if (GDeathnote.getConfiguration().getBoolean("config.broadcast-death")) {
                    String message = GDeathnote.getConfiguration().getString("messages.broadcast-message").replace("{killed}", target.getName()).replace("{player}", e.getPlayer().getName());
                    Bukkit.broadcastMessage(translateColor(message));
                }
                e.getPlayer().getInventory().setItemInMainHand(item);
                e.getPlayer().updateInventory();
                if (GDeathnote.getConfiguration().getBoolean("config.message-death")) {
                    target.sendMessage(translateColor(GDeathnote.getConfiguration().getString("messages.playerdeath-message").replace("{killed}", target.getName()).replace("{player}", e.getPlayer().getName())));
                }
                target.setMetadata("KILLEDBYDN", new FixedMetadataValue(GDeathnote.getPlugin(GDeathnote.class), true));
/*            items.put(target, target.getInventory().getContents());
            armors.put(target, target.getInventory().getArmorContents());
            extra.put(target, target.getInventory().getExtraContents());*/
                target.setHealth(0.0D);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if (player.hasMetadata("KILLEDBYDN")) {
            e.getDrops().clear();
            e.setDeathMessage(null);
            e.setKeepInventory(true);
            if (GDeathnote.getConfiguration().getBoolean("config.respawn-fast")) {
                player.spigot().respawn();
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        if (player.hasMetadata("KILLEDBYDN")) {
            player.removeMetadata("KILLEDBYDN", GDeathnote.getPlugin(GDeathnote.class));
/*            player.getInventory().setContents(items.get(player));
            player.getInventory().setArmorContents(armors.get(player));
            player.getInventory().setExtraContents(extra.get(player));*/
        }
    }

    public String translateColor(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
