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

    private static AchievementStatus getStatus(Long now, List<Long> conf){
        String stage;
        String color;
        if(now >= conf.getLast()){
            stage = Achievement_GOLD;
            color = Name_GOLD;
        }else if(now >= conf.getFirst()){
            stage = Achievement_SILVER;
            color = Name_SILVER;
        }else{
            stage = Achievement_NORMAL;
            color = Name_NORMAL;
        }
        return AchievementStatus.create(
                now >= conf.getLast(),
                (now >= conf.getFirst() ? conf.getLast():conf.getFirst()),
                stage,
                color
        );
    }

    @Nullable
    public static AchievementData getFlower(Player player) {
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long place = lifeCount.getFlower_place();
        List<Long> flower = AchievementConfig.getInstance().getFlower();
        AchievementStatus status = getStatus(place, flower);
        return AchievementData.create(
                status.getStage(),
                status.getColor() + "花を植えた数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+place + "/" + status.getGoal()
                ))
        );
    }

    @Nullable
    public static AchievementData getBreak(Player player) {
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long breakCount = lifeCount.getBlock_break();
        List<Long> blockBreak = AchievementConfig.getInstance().getBreak();
        AchievementStatus status = getStatus(breakCount, blockBreak);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"ブロック破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+breakCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getPlace(Player player){
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long placeCount = lifeCount.getBlock_place();
        List<Long> place = AchievementConfig.getInstance().getPlace();
        AchievementStatus status = getStatus(placeCount, place);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"ブロック設置数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+placeCount+"/"+status.getGoal()
                ))
        );
    }
}
