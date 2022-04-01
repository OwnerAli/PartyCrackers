package me.ogali.partycrackers.runnables;

import me.ogali.partycrackers.PartyCrackersPlugin;
import me.ogali.partycrackers.partycrackers.PartyCracker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

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
        PartyCrackersPlugin.getInstance().getPartyCrackerHandler().removeDroppedPartyCracker(droppedPartyCracker);
        droppedPartyCracker.remove();

        // PARTY CRACKER LOCATION
        Location partyCrackerLocation = droppedPartyCracker.getLocation();

        Location particleEffectLocationLeft = droppedPartyCracker.getLocation().clone().add(-1.5, 1, 0);
        Location particleEffectLocationRight = droppedPartyCracker.getLocation().clone().add(1.5, 1.5, 0);
        Location particleEffectLocationFront = droppedPartyCracker.getLocation().clone().add(0, 1.5, 1.5);
        Location particleEffectLocationBack = droppedPartyCracker.getLocation().clone().add(0, 1, -1.5);

        World partyCrackerWorld = partyCrackerLocation.getWorld();
        partyCracker.getRewards().forEach(reward -> {
            if (reward.getReward() != null) {
                partyCrackerWorld.dropItem(partyCrackerLocation, reward.getReward());
            }
        });

        Random random = PartyCrackersPlugin.getInstance().getRandom();

        // GET RANDOM COLOR FROM LIST AND SPAWN IT
        String randomColor;

        if (partyCracker.getParticles().size() == 1) {
            randomColor = partyCracker.getParticles().get(0);
        } else {
            randomColor = partyCracker.getParticles().get(random.nextInt(partyCracker.getParticles().size()));
        }

        partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationLeft, 1,
                new Particle.DustOptions(partyCracker.getColorFromString(randomColor), 1));
        partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationRight, 1,
                new Particle.DustOptions(partyCracker.getColorFromString(randomColor), 1));
        partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationBack, 1,
                new Particle.DustOptions(partyCracker.getColorFromString(randomColor), 1));
        partyCrackerWorld.spawnParticle(Particle.REDSTONE, particleEffectLocationFront, 1,
                new Particle.DustOptions(partyCracker.getColorFromString(randomColor), 1));

        // GET RANDOM SOUND FROM LIST AND PLAY IT
        Sound randomSound;

        if (partyCracker.getSounds().size() == 1) {
            randomSound = partyCracker.getSounds().get(0);
        } else {
            randomSound = partyCracker.getSounds().get(random.nextInt(partyCracker.getSounds().size()));
        }

        partyCrackerWorld.playSound(partyCrackerLocation, randomSound, 10, 10);
    }

    public void startTimer() {
        this.runTaskLater(PartyCrackersPlugin.getInstance(), partyCracker.getTimeTillExplodeInSeconds() * 20);
    }


}
