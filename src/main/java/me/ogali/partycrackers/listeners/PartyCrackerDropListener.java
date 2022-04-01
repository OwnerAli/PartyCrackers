package me.ogali.partycrackers.listeners;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.partycrackers.PartyCracker;
import me.ogali.partycrackers.rewards.Reward;
import me.ogali.partycrackers.runnables.RemoveItemRunnable;
import me.ogali.partycrackers.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PartyCrackerDropListener implements Listener {

    private final PartyCrackersPlugin main;

    public PartyCrackerDropListener(PartyCrackersPlugin main) {
        this.main = main;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().isDead()) return;
        main.getPartyCrackerHandler().getAllPartyCrackers().forEach(partyCracker -> {
            if (partyCracker.getPartyCrackerItem().equals(event.getItemDrop().getItemStack())) {
                main.getPartyCrackerHandler().addDroppedPartyCracker(event.getItemDrop());
                event.getItemDrop().setGlowing(true);
                new RemoveItemRunnable(partyCracker, event.getItemDrop()).startTimer();
            }
        });
    }

    @EventHandler
    public void onPickUp(EntityPickupItemEvent event) {
        if (!main.getPartyCrackerHandler().isDroppedPartyCracker(event.getItem())) return;
        event.setCancelled(true);
    }


}
/*        List<Reward> rewards = new ArrayList<>();
        List<String> particles = new ArrayList<>();
        List<Sound> sounds = new ArrayList<>();

        particles.add("AQUA");
        sounds.add(Sound.ENTITY_PLAYER_LEVELUP);
        rewards.add(new Reward("super_dirt", new ItemBuilder(Material.DIRT).setName("&fSUPER DIRT")
                .addLoreLines("&fI am super dirt.", "&fI'm better than regular dirt.").setGlow().build(),
                100.0, 1, 4));
        PartyCracker partyCracker = new PartyCracker("blue_party_cracker",
                new ItemBuilder(Material.WARPED_BUTTON).setName("&9&lBLUE PARTY CRACKER")
                        .addLoreLine("&fDrop me for a surprise!").build(), 5, rewards, particles, sounds);*/