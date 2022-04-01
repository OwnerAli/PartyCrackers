package me.ogali.partycrackers.partycrackers;

import org.bukkit.entity.Item;

import java.util.*;

public class PartyCrackerHandler {

    private final Map<String, PartyCracker> partyCrackersMap = new HashMap<>();
    private final Set<Item> droppedPartyCrackersSet = new HashSet<>();

    public void addPartyCracker(PartyCracker partyCracker) {
        partyCrackersMap.put(partyCracker.getId(), partyCracker);
    }

    public PartyCracker getPartyCrackerById(String id) {
        return partyCrackersMap.get(id);
    }

    public Set<String> getAllPartyCrackerIds() {
        return partyCrackersMap.keySet();
    }

    public Collection<PartyCracker> getAllPartyCrackers() {
        return partyCrackersMap.values();
    }

    public void addDroppedPartyCracker(Item droppedPartyCracker) {
        droppedPartyCrackersSet.add(droppedPartyCracker);
    }

    public void removeDroppedPartyCracker(Item droppedPartyCracker) {
        droppedPartyCrackersSet.remove(droppedPartyCracker);
    }

    public boolean isDroppedPartyCracker(Item droppedItem) {
        return droppedPartyCrackersSet.contains(droppedItem);
    }
}
