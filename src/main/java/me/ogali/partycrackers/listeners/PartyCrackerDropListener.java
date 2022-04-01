package me.ogali.partycrackers.listeners;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.runnables.RemoveItemRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

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