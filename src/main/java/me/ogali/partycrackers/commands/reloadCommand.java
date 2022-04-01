package me.ogali.partycrackers.commands;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("partycracker") || label.equalsIgnoreCase("pcracker"))) return false;
        if (!(sender instanceof Player player)) return false;

        if (args.length > 0) {
            switch(args.length) {
                case 1 -> Chat.tell(player, "&cUsage: /pcracker reload <file>");
                case 2 -> {
                    if (args[1].equalsIgnoreCase("partycrackerfile")) {
                        PartyCrackersPlugin.getInstance().getPartyCrackersFile().reloadFile();
                        Chat.tell(player, "&aPartyCracker file successfully reloaded!");
                        return true;
                    } else if (args[1].equalsIgnoreCase("rewardsfile")) {
                        PartyCrackersPlugin.getInstance().getRewardsFile().reloadFile();
                        Chat.tell(player, "&aRewards file successfully reloaded!");
                        return true;
                    }
                }
            }
        } else {
            Chat.tell(player, "&cUsage: /pcracker give <player> <partycracker name> <amount> | /pcracker reload <file>");
        }
        return false;
    }
}
