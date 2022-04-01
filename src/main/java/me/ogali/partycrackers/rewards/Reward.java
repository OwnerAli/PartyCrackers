package me.ogali.partycrackers.rewards;

import me.ogali.partycrackers.PartyCrackersPlugin;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Reward {

    private final String id;
    private final ItemStack reward;
    private final double chance;
    private final int amountOfRewardsMin;
    private final int amountOfRewardsMax;
    private final Random random;

    public Reward(String id, ItemStack reward, double chance, int amountOfRewardsMin, int amountOfRewardsMax) {
        this.id = id;
        this.reward = reward;
        this.chance = chance;
        this.amountOfRewardsMin = amountOfRewardsMin;
        this.amountOfRewardsMax = amountOfRewardsMax;
        random = PartyCrackersPlugin.getInstance().getRandom();
    }

    public String getId() {
        return id;
    }

    public ItemStack getReward() {
        if (getDropAmount() != 0 && isDrop()) {
            reward.setAmount(getDropAmount());
            return reward;
        }
        return null;
    }

    public double getChance() {
        return chance;
    }

    public int getAmountOfRewardsMin() {
        return amountOfRewardsMin;
    }

    public int getAmountOfRewardsMax() {
        return amountOfRewardsMax;
    }

    private boolean isDrop() {
        if (chance == 100.0) {
            return true;
        }
        double randomDouble = Math.round(10.0 * (101 * random.nextDouble())) / 10.0;
        return randomDouble <= chance;
    }

    private int getDropAmount() {
        return random.nextInt(amountOfRewardsMax - amountOfRewardsMin + 1) + amountOfRewardsMin;
    }
}
