package fr.communaywen.core.dreamdim.fishing;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class LootCategory {
    @NotNull
    public abstract Set<LootStack> getLoots();

    @NotNull
    public abstract String getName();

    @NotNull
    public abstract Double getChance();

    public final boolean addToLoot(LootStack lootStack) {
        return getLoots().add(lootStack);
    }

    public final boolean removeFromLoot(LootStack lootStack) {
        return getLoots().remove(lootStack);
    }

    public LootStack pickOne(int LureLevel) {
        //ChatGPT :alien:
        double totalChance = 0.0;
        for (LootStack stack : getLoots()) {
            totalChance += stack.getChance(LureLevel);
        }

        if (totalChance != 1) {
            throw new IllegalArgumentException("LootCategory " + getName() + " has chance " + totalChance);
        }

        Random random = new Random();
        double randomValue = random.nextDouble();

        double cumulativeChance = 0.0;
        for (LootStack entry : getLoots()) {
            cumulativeChance += entry.getChance(LureLevel);
            if (randomValue <= cumulativeChance) {
                return entry;
            }
        }

        // This return statement should never be reached, but is required for compilation.
        throw new IllegalStateException("Loot picking failed");
    }

    // Method to pick 'x' loots
    public List<LootStack> pick(int x, Integer LureLevel) {
        List<LootStack> pickedLoots = new ArrayList<>();

        for (int i = 0; i < x; i++) {
            pickedLoots.add(pickOne(LureLevel));
        }

        return pickedLoots;
    }
}
