package me.ogali.partycrackers.commands;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.partycrackers.PartyCracker;
import me.ogali.partycrackers.utils.Chat;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PartyCrackerGiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("partycracker") || label.equalsIgnoreCase("pcracker"))) return false;
        if (!(sender instanceof Player player)) return false;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("give")) {
                switch (args.length) {
                    case 1, 2, 3 -> {
                        Chat.tell(player, "&cUsage: /pcracker give <player> <partycracker name> <amount>");
                        return false;
                    }
                    case 4 -> {
                        if (Bukkit.getPlayer(args[1]) == null) {
                            Chat.tell(player, "&cInvalid or offline player.");
                            return false;
                        }
                        if (PartyCrackersPlugin.getInstance().getPartyCrackerHandler().getPartyCrackerById(args[2]) == null) {
                            Chat.tell(player, "&cInvalid PartyCracker id.");
                            return false;
                        }
                        if (!NumberUtils.isNumber(args[3])) {
                            Chat.tell(player, "&cInvalid number.");
                            return false;
                        }

                        Player specifiedPlayer = Bukkit.getPlayer(args[1]);
                        PartyCracker specifiedPartyCracker = PartyCrackersPlugin.getInstance().getPartyCrackerHandler().getPartyCrackerById(args[2]);
                        int specifiedAmount = Integer.parseInt(args[3]);
                        ItemStack partyCrackerItem = specifiedPartyCracker.getPartyCrackerItem().clone();
                        partyCrackerItem.setAmount(specifiedAmount);

                        if (specifiedPlayer.getInventory().firstEmpty() != -1) {
                            specifiedPlayer.getInventory().addItem(partyCrackerItem);
                            Chat.tell(specifiedPlayer, "&aYou were given &f" + specifiedAmount + "x " + specifiedPartyCracker.getId() + "(s)");
                        } else {
                            specifiedPlayer.getWorld().dropItem(specifiedPlayer.getLocation(), partyCrackerItem);
                            Chat.tell(specifiedPlayer, "&cYour inventory was full so &f" + specifiedAmount + "x " + specifiedPartyCracker.getId() + "(s) &cwere dropped at your feet.");
                        }
                    }
                }

            }
        } else {
            Chat.tell(player, "&cUsage: /pcracker give <player> <partycracker name> <amount> | /pcracker reload");
        }
        return false;
    }
}
