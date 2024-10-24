package space.yurisi.universecorev2.subplugins.achievement.data;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public final class AchievementData {

    private final String stage;
    private final String itemName;
    private final ArrayList<String> itemLore;

    private AchievementData(@NotNull String stage, @NotNull String itemName, @NotNull ArrayList<String> itemLore){
        this.stage = stage;
        this.itemName = itemName;
        this.itemLore = itemLore;
    }

    public static AchievementData createData(@NotNull String stage, @NotNull String ItemName, @NotNull ArrayList<String> ItemLore){
        return new AchievementData(stage, ItemName, ItemLore);
    }

    public String getStage() {
        return stage;
    }

    public String getItemName() {
        return itemName;
    }

    public ArrayList<String> getItemLore() {
        return itemLore;
    }
}
