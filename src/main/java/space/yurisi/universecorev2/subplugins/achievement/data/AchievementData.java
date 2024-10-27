package space.yurisi.universecorev2.subplugins.achievement.data;

import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class AchievementData {

    private final String stage;
    private final String itemName;
    private final ArrayList<String> itemLore;
    private final Long currentGoal;
    private final Long currentCount;
    private final Material material;

    private AchievementData(@NotNull Material material, @NotNull Long currentCount, @NotNull Long currentGoal, @NotNull String stage, @NotNull String itemName, @NotNull ArrayList<String> itemLore){
        this.material = material;
        this.currentCount = currentCount;
        this.currentGoal = currentGoal;
        this.stage = stage;
        this.itemName = itemName;
        this.itemLore = itemLore;
    }

    public static AchievementData create(@NotNull Material material, @NotNull Long currentCount, @NotNull Long currentGoal, @NotNull String stage, @NotNull String ItemName, @NotNull ArrayList<String> ItemLore){
        return new AchievementData(material, currentCount, currentGoal, stage, ItemName, ItemLore);
    }

    @NotNull
    public Material getMaterial() {
        return material;
    }

    @NotNull
    public Long getCurrentCount() {
        return currentCount;
    }

    @NotNull
    public Long getCurrentGoal() {
        return currentGoal;
    }

    @NotNull
    public String getStage() {
        return stage;
    }

    @NotNull
    public String getItemName() {
        return itemName;
    }

    @NotNull
    public ArrayList<String> getItemLore() {
        return itemLore;
    }
}
