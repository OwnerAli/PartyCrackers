package me.ogali.partycrackers.partycrackers;

import me.ogali.partycrackers.rewards.Reward;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PartyCracker {

    private final String id;
    private final ItemStack partyCrackerItem;
    private final long timeTillExplodeInSeconds;
    private final List<Reward> rewards;
    private final List<String> particles;
    private final List<Sound> sounds;

    public PartyCracker(String id, ItemStack partyCrackerItem, long timeTillExplodeInSeconds, List<Reward> rewards, List<String> particles, List<Sound> sounds) {
        this.id = id;
        this.partyCrackerItem = partyCrackerItem;
        this.timeTillExplodeInSeconds = timeTillExplodeInSeconds;
        this.rewards = rewards;
        this.particles = particles;
        this.sounds = sounds;
    }

    public String getId() {
        return id;
    }

    public ItemStack getPartyCrackerItem() {
        return partyCrackerItem;
    }

    public long getTimeTillExplodeInSeconds() {
        return timeTillExplodeInSeconds;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public List<String> getParticles() {
        return particles;
    }

    public List<Sound> getSounds() {
        return sounds;
    }

    public Color getColorFromString(String color) {
        switch(color.toUpperCase()) {
            case "BLACK":
                return Color.BLACK;
            case "GRAY":
                return Color.GRAY;
            case "SILVER":
                return Color.SILVER;
            case "WHITE":
                return Color.WHITE;
            case "RED":
                return Color.RED;
            case "MAROON":
                return Color.MAROON;
            case "ORANGE":
                return Color.ORANGE;
            case "YELLOW":
                return Color.YELLOW;
            case "GREEN":
                return Color.GREEN;
            case "LIME":
                return Color.LIME;
            case "OLIVE":
                return Color.OLIVE;
            case "BLUE":
                return Color.BLUE;
            case "NAVY":
                return Color.NAVY;
            case "AQUA":
                return Color.AQUA;
            case "TEAL":
                return Color.TEAL;
            case "PURPLE":
                return Color.PURPLE;
            case "PINK":
                return Color.FUCHSIA;
        }
        return null;
    }
}
