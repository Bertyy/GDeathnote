package com.gdeathnote.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class GDeathnoteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2 || !args[0].equalsIgnoreCase("givebook")) {
            sender.sendMessage("§eUtilize: /" + label + " givebook <jogador>");
        } else {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJogador não encontrado.");
                return false;
            }
            if (containsByName(target, "§cDeathnote")) {
                sender.sendMessage("§cO jogador ´" + target.getName() + "´ já tem o Deathnote em suas mãos.");
            } else {
                ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
                BookMeta bookMeta = (BookMeta) item.getItemMeta();
                bookMeta.setPages("§7Digite o nome aqui...");
                bookMeta.setDisplayName("§cDeathnote");
                item.setItemMeta(bookMeta);
                target.getInventory().addItem(item);
                sender.sendMessage("§aO jogador ´" + target.getName() + "´ agora tem o Deathnote.");
            }
        }
        return false;
    }

    public boolean containsByName(Player player, String displayName) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && !item.getType().equals(Material.AIR)) {
                if (item.hasItemMeta()) {
                    if (item.getItemMeta().hasDisplayName()) {
                        if (item.getItemMeta().getDisplayName().equals(displayName)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
