package me.ogali.partycrackers.commands;

import me.ogali.partycrackers.PartyCrackersPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class partyCrackerCommandCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> result = new ArrayList<>();

        if (args.length == 1) {
            result.add("give");
            return result;
        } else if (args.length == 2) {
            Bukkit.getOnlinePlayers().forEach(player -> result.add(player.getName()));
            return result;
        } else if (args.length == 3) {
            result.addAll(PartyCrackersPlugin.getInstance().getPartyCrackerHandler().getAllPartyCrackerIds());
            return result;
        }

        return null;
    }

}
