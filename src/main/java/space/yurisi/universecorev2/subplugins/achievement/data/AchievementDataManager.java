package space.yurisi.universecorev2.subplugins.achievement.data;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.models.count.LifeCount;
import space.yurisi.universecorev2.subplugins.achievement.data.config.AchievementConfig;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AchievementDataManager {

    public static final String Achievement_NORMAL = "normal";
    public static final String Achievement_SILVER = "silver";
    public static final String Achievement_GOLD = "gold";
    public static final String Name_GOLD = "§e";
    public static final String Name_SILVER = "§b";
    public static final String Name_NORMAL = "§f";

    public static CounterModelManager manager = null;

    public static void setManager(CounterModelManager manager) {
        AchievementDataManager.manager = manager;
    }

    @Nullable
    public static AchievementData getFlower(Player player) {
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long place = lifeCount.getFlower_place();
        List<Long> flower = AchievementConfig.getInstance().getFlower();
        Long goal;
        String stage;
        String color;
        if(place >= flower.getLast()) {
            goal = flower.getLast();
            stage = Achievement_GOLD;
            color = Name_GOLD;
        }else if (place >= flower.getFirst()) {
            goal = flower.getLast();
            stage = Achievement_SILVER;
            color = Name_SILVER;
        }else{
            goal = flower.getFirst();
            stage = Achievement_NORMAL;
            color = Name_NORMAL;
        }

        return AchievementData.createData(
                stage,
                color + "花を植えた数",
                new ArrayList<>(List.of(
                        place + "/" + goal
                ))
        );
    }

    public static AchievementData getBreak(Player player) {
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long breakCount = lifeCount.getBlock_break();
        List<Long> blockBreak = AchievementConfig.getInstance().getBreak();
        Long goal;
        String stage;
        String color;
        if(breakCount >= blockBreak.getLast()) {
            goal = blockBreak.getLast();
            stage = Achievement_GOLD;
            color = Name_GOLD;
        }else if (breakCount >= blockBreak.getFirst()) {
            goal = blockBreak.getLast();
            stage = Achievement_SILVER;
            color = Name_SILVER;
        }else{
            goal = blockBreak.getFirst();
            stage = Achievement_NORMAL;
            color = Name_NORMAL;
        }
        return AchievementData.createData(
                stage,
                color+"ブロック破壊数",
                new ArrayList<>(List.of(
                        breakCount+"/"+goal
                ))
        );
    }
}
