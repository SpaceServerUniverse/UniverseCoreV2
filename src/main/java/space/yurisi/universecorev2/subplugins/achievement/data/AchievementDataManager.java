package space.yurisi.universecorev2.subplugins.achievement.data;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.models.count.LifeCount;
import space.yurisi.universecorev2.database.models.count.OreCount;
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

    public static boolean canGetManager(){
        return manager != null;
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

    public static AchievementData getCoal(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long coalCount = oreCount.getCoal_ore();
        List<Long> coal = AchievementConfig.getInstance().getCoal();
        AchievementStatus status = getStatus(coalCount, coal);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"石炭:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+coalCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getIron(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long ironCount = oreCount.getIron_ore();
        List<Long> iron = AchievementConfig.getInstance().getIron();
        AchievementStatus status = getStatus(ironCount, iron);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"鉄:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+ironCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getGold(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long goldCount = oreCount.getGold_ore();
        List<Long> gold = AchievementConfig.getInstance().getGold();
        AchievementStatus status = getStatus(goldCount, gold);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"金:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+goldCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getLapis(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long lapisCount = oreCount.getLapis_ore();
        List<Long> lapis = AchievementConfig.getInstance().getLapis();
        AchievementStatus status = getStatus(lapisCount, lapis);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"ラピス:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+lapisCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getRedStone(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long redStoneCount = oreCount.getRedstone_ore();
        List<Long> redStone = AchievementConfig.getInstance().getRedStone();
        AchievementStatus status = getStatus(redStoneCount, redStone);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"赤石:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+redStoneCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getEmerald(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long emeraldCount = oreCount.getEmerald_ore();
        List<Long> emerald = AchievementConfig.getInstance().getEmerald();
        AchievementStatus status = getStatus(emeraldCount, emerald);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"エメラルド:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+emeraldCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getDiamond(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long diamondCount = oreCount.getDiamond_ore();
        List<Long> diamond = AchievementConfig.getInstance().getDiamond();
        AchievementStatus status = getStatus(diamondCount, diamond);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"ダイヤ:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+diamondCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getCopper(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long copperCount = oreCount.getCopper_ore();
        List<Long> copper = AchievementConfig.getInstance().getCopper();
        AchievementStatus status = getStatus(copperCount, copper);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"銅:原石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+copperCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getOre(Player player){
        if(manager == null) return null;
        OreCount oreCount = manager.get(player).getOreCount();
        Long oreSum = (
                  oreCount.getCoal_ore()
                + oreCount.getIron_ore()
                + oreCount.getGold_ore()
                + oreCount.getLapis_ore()
                + oreCount.getRedstone_ore()
                + oreCount.getEmerald_ore()
                + oreCount.getDiamond_ore()
                + oreCount.getCopper_ore()
        );

        List<Long> ore = AchievementConfig.getInstance().getOre();
        AchievementStatus status = getStatus(oreSum, ore);
        return AchievementData.create(
                status.getStage(),
                status.getColor()+"鉱石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+oreSum+"/"+status.getGoal()
                ))
        );
    }
}
