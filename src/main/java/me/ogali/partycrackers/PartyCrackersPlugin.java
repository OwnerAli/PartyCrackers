package me.ogali.partycrackers;

import me.ogali.partycrackers.commands.PartyCrackerGiveCommand;
import me.ogali.partycrackers.commands.partyCrackerCommandCompletion;
import me.ogali.partycrackers.files.File;
import me.ogali.partycrackers.listeners.PartyCrackerDropListener;
import me.ogali.partycrackers.partycrackers.PartyCracker;
import me.ogali.partycrackers.partycrackers.PartyCrackerHandler;
import me.ogali.partycrackers.rewards.Reward;
import me.ogali.partycrackers.rewards.RewardHandler;
import me.ogali.partycrackers.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class PartyCrackersPlugin extends JavaPlugin {

    private static PartyCrackersPlugin instance;
    private PartyCrackerHandler partyCrackerHandler;
    private RewardHandler rewardHandler;
    private Random random;
    private File partyCrackersFile;
    private File rewardsFile;

    @Override
    public void onEnable() {
        instance = this;
        this.random = new Random();
        registerListeners();
        registerHandlers();
        registerFiles();
        loadRewards();
        loadPartyCrackers();
        Objects.requireNonNull(getCommand("partycracker")).setExecutor(new PartyCrackerGiveCommand());
        Objects.requireNonNull(getCommand("partycracker")).setTabCompleter(new partyCrackerCommandCompletion());
    }

    @Override
    public void onDisable() {
        partyCrackersFile.saveFile();
        rewardsFile.saveFile();
    }

    public Random getRandom() {
        return random;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PartyCrackerDropListener(this), this);
    }

    private void registerHandlers() {
        partyCrackerHandler = new PartyCrackerHandler();
        rewardHandler = new RewardHandler();
    }

    private void registerFiles() {
        partyCrackersFile = new File(this, "partycrackers");
        rewardsFile = new File(this, "rewards");
    }

    private void loadRewards() {
        FileConfiguration rewardsConfigFile = rewardsFile.getFile();

        // ITERATE THRU REWARD FILE KEYS AND CREATE NEW REWARD OBJECT(S)
        rewardsConfigFile.getKeys(false).forEach(rewardId -> {

            // REWARD OBJECT CONFIG VALUES
            String material = rewardsConfigFile.getString(rewardId + ".item.material");
            String name = rewardsConfigFile.getString(rewardId + ".item.name");
            List<String> lore = rewardsConfigFile.getStringList(rewardId + ".item.lore");
            boolean glow = rewardsConfigFile.getBoolean(rewardId + ".item.glow");
            double chance = rewardsConfigFile.getDouble(rewardId + ".chance");
            int min = rewardsConfigFile.getInt(rewardId + ".min");
            int max = rewardsConfigFile.getInt(rewardId + ".max");

            // BUILDING REWARD ITEM WITH CONFIG VALUES
            ItemBuilder itemBuilder = new ItemBuilder(Material.getMaterial(material)).setName(name).setLore(lore);
            ItemStack rewardItem;
            if (glow) {
                rewardItem = itemBuilder.setGlow().build();
            } else {
                rewardItem = itemBuilder.build();
            }

            // ADD NEW REWARDS TO MAP FOR LATER USE
            rewardHandler.addReward(new Reward(rewardId, rewardItem, chance, min, max));
        });
    }

    private void loadPartyCrackers() {
        FileConfiguration file = partyCrackersFile.getFile();

        // ITERATE THRU PARTYCRACKERS FILE KEYS AND CREATE NEW PARTYCRACKER OBJECT(S)
        file.getKeys(false).forEach(partyCrackerId -> {

            // PARTYCRACKER CONFIG VALUES
            String material = file.getString(partyCrackerId + ".item.material");
            String name = file.getString(partyCrackerId + ".item.name");
            List<String> lore = file.getStringList(partyCrackerId + ".item.lore");
            boolean glow = file.getBoolean(partyCrackerId + ".item.glow");
            int timeTillExplode = file.getInt(partyCrackerId + ".time-till-explode-in-seconds");
            List<String> rewardIdsList = file.getStringList(partyCrackerId + ".rewards");
            List<String> particleColorsList = file.getStringList(partyCrackerId + ".particle-colors");
            List<String> soundStringList = file.getStringList(partyCrackerId + ".sounds");

            // GETTING REWARDS
            List<Reward> rewards = rewardHandler.getRewardsFromList(rewardIdsList);

            // CREATING SOUNDS
            List<Sound> soundsList = new ArrayList<>();
            soundStringList.forEach(soundString -> soundsList.add(Sound.valueOf(soundString)));

            // BUILDING PARTYCRACKER ITEM WITH CONFIG VALUES
            ItemBuilder itemBuilder = new ItemBuilder(Material.getMaterial(material)).setName(name).setLore(lore);
            ItemStack partyCrackerItem;
            if (glow) {
                partyCrackerItem = itemBuilder.setGlow().build();
            } else {
                partyCrackerItem = itemBuilder.build();
            }

            // ADD PARTYCRACKER(S) TO HASHMAP FOR LATER LOOKUP
            partyCrackerHandler.addPartyCracker(new PartyCracker(partyCrackerId, partyCrackerItem, timeTillExplode, rewards, particleColorsList, soundsList));
        });
    }

    public static PartyCrackersPlugin getInstance() {
        return instance;
    }

    public PartyCrackerHandler getPartyCrackerHandler() {
        return partyCrackerHandler;
    }

    public File getPartyCrackersFile() {
        return partyCrackersFile;
    }

}
