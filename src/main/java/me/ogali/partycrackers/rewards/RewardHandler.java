package me.ogali.partycrackers.rewards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RewardHandler {

    private final Map<String, Reward> rewardsMap = new HashMap<>();

    public void addReward(Reward reward) {
        rewardsMap.put(reward.getId(), reward);
    }

    public List<Reward> getRewardsFromList(List<String> rewardIds) {
        List<Reward> rewardsList = new ArrayList<>();
        rewardIds.forEach(id -> {
            if (rewardsMap.get(id) != null) {
                rewardsList.add(rewardsMap.get(id));
            }
        });
        return rewardsList;
    }
}
