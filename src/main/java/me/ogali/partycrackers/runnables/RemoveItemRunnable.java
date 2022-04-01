package me.ogali.partycrackers.runnables;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.partycrackers.PartyCracker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveItemRunnable extends BukkitRunnable {

    private final PartyCracker partyCracker;
    private final Item droppedPartyCracker;

    public RemoveItemRunnable(PartyCracker partyCracker, Item droppedPartyCracker) {
        this.partyCracker = partyCracker;
        this.droppedPartyCracker = droppedPartyCracker;
    }

    @Override
    public void run() {
        // REMOVE ENTITY FROM FLOOR
        droppedPartyCracker.remove();

        // PARTY CRACKER LOCATION
        Location partyCrackerLocation = droppedPartyCracker.getLocation();

        Location particleEffectLocationRight = droppedPartyCracker.getLocation().clone().add(-1.5, 1, 0);
        Location particleEffectLocationLeft1 = droppedPartyCracker.getLocation().clone().add(1.5, 1.5, 0);
        Location particleEffectLocationMiddle1 = droppedPartyCracker.getLocation().clone().add(0, 1, -1.5);
        Location particleEffectLocationMiddle2 = droppedPartyCracker.getLocation().clone().add(0, 1.5, 1.5);

        World partyCrackerWorld = partyCrackerLocation.getWorld();
        partyCracker.getRewards().forEach(reward -> {
            if (reward.getReward() != null) {
                partyCrackerWorld.dropItem(partyCrackerLocation, reward.getReward());
            }
        });
        partyCracker.getParticles().forEach(particle -> {
            partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationRight, 1,
                    new Particle.DustOptions(partyCracker.getColorFromString(particle), 1));
            partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationLeft1, 1,
                    new Particle.DustOptions(partyCracker.getColorFromString(particle), 1));
            partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationMiddle1, 1,
                    new Particle.DustOptions(partyCracker.getColorFromString(particle), 1));
            partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationMiddle2, 1,
                    new Particle.DustOptions(partyCracker.getColorFromString(particle), 1));
        });
        partyCracker.getSounds().forEach(sound -> partyCrackerWorld.playSound(partyCrackerLocation, sound, 10, 10));
    }

    public void startTimer() {
        this.runTaskLater(PartyCrackersPlugin.getInstance(), partyCracker.getTimeTillExplodeInSeconds() * 20);
    }


}
