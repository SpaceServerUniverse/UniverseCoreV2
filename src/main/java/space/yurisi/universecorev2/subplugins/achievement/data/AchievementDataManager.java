package space.yurisi.universecorev2.subplugins.achievement.data;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.models.count.KillDeathCount;
import space.yurisi.universecorev2.database.models.count.LifeCount;
import space.yurisi.universecorev2.database.models.count.OreCount;
import space.yurisi.universecorev2.database.models.count.PlayerCount;
import space.yurisi.universecorev2.subplugins.achievement.data.config.AchievementConfig;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;

import java.util.ArrayList;
import java.util.List;

public class AchievementDataManager {

    public static final String Achievement_NORMAL = "normal";
    public static final String Achievement_SILVER = "silver";
    public static final String Achievement_GOLD = "gold";
    public static final String Name_NORMAL = "§f";
    public static final String Name_SILVER = "§b";
    public static final String Name_GOLD = "§e";

    public static CounterModelManager manager = null;

    public static void setManager(CounterModelManager manager) {
        AchievementDataManager.manager = manager;
    }

    public static boolean canGetManager(){
        return manager != null;
    }

    private static AchievementStatus getStatus(long now, List<Long> conf) {
        if (conf == null || conf.isEmpty()) {
            // 仕様に合わせて：0扱い / 例外 / NORMAL固定など
            return AchievementStatus.create(false, 0L, Achievement_NORMAL, Name_NORMAL);
        }

        long first = conf.get(0);
        long last = conf.get(conf.size() - 1);

        String stage;
        String color;

        if (now >= last) {
            stage = Achievement_GOLD;
            color = Name_GOLD;
        } else if (now >= first) {
            stage = Achievement_SILVER;
            color = Name_SILVER;
        } else {
            stage = Achievement_NORMAL;
            color = Name_NORMAL;
        }

        return AchievementStatus.create(
                now >= last,
                (now >= first ? last : first),
                stage,
                color
        );
    }

    public static AchievementData getFlower(Player player) {
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long place = lifeCount.getFlower_place();
        List<Long> flower = AchievementConfig.getInstance().getFlower();
        AchievementStatus status = getStatus(place, flower);
        return AchievementData.create(
                Material.POPPY,
                place,
                status.getGoal(),
                status.getStage(),
                status.getColor() + "花を植えた数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+place + "/" + status.getGoal()
                ))
        );
    }

    public static AchievementData getBreak(Player player) {
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long breakCount = lifeCount.getBlock_break();
        List<Long> blockBreak = AchievementConfig.getInstance().getBreak();
        AchievementStatus status = getStatus(breakCount, blockBreak);
        return AchievementData.create(
                Material.IRON_PICKAXE,
                breakCount,
                status.getGoal(),
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
                Material.OAK_PLANKS,
                placeCount,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"ブロック設置数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+placeCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getFishing(Player player){
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long fishingCount = lifeCount.getFishing();
        List<Long> fishing = AchievementConfig.getInstance().getFishing();
        AchievementStatus status = getStatus(fishingCount, fishing);
        return AchievementData.create(
                Material.FISHING_ROD,
                fishingCount,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"釣りをした回数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+fishingCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getGacha(Player player){
        if(manager == null) return null;
        LifeCount lifeCount = manager.get(player).getLifeCount();
        Long gachaCount = lifeCount.getGacha();
        List<Long> gacha = AchievementConfig.getInstance().getGacha();
        AchievementStatus status = getStatus(gachaCount, gacha);
        return AchievementData.create(
                Material.PAPER,
                gachaCount,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"ガチャを引いた回数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+gachaCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getKill(Player player){
        if(manager == null) return null;
        KillDeathCount killDeathCount = manager.get(player).getKillDeathCount();
        Long killCount = killDeathCount.getPlayer_kill();
        List<Long> kill = AchievementConfig.getInstance().getKill();
        AchievementStatus status = getStatus(killCount, kill);
        return AchievementData.create(
                Material.IRON_SWORD,
                killCount,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"キルした回数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+killCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getLogin(Player player){
        if(manager == null) return null;
        PlayerCount playerCount = manager.get(player).getPlayerCount();
        Long loginCount = playerCount.getLogin();
        List<Long> login = AchievementConfig.getInstance().getLogin();
        AchievementStatus status = getStatus(loginCount, login);
        return AchievementData.create(
                Material.LANTERN,
                loginCount,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"サーバーログイン日数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+loginCount+"/"+status.getGoal()
                ))
        );
    }

    public static AchievementData getConsecutiveLogin(Player player){
        if(manager == null) return null;
        PlayerCount playerCount = manager.get(player).getPlayerCount();
        Long loginCount = playerCount.getConsecutive_login();
        List<Long> login = AchievementConfig.getInstance().getConsecutiveLogin();
        AchievementStatus status = getStatus(loginCount, login);
        return AchievementData.create(
                Material.SOUL_LANTERN,
                loginCount,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"サーバー連続ログイン日数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+loginCount+"/"+status.getGoal()
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
                Material.COAL_ORE,
                coalCount,
                status.getGoal(),
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
                Material.IRON_ORE,
                ironCount,
                status.getGoal(),
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
                Material.GOLD_ORE,
                goldCount,
                status.getGoal(),
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
                Material.LAPIS_ORE,
                lapisCount,
                status.getGoal(),
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
                Material.REDSTONE_ORE,
                redStoneCount,
                status.getGoal(),
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
                Material.EMERALD_ORE,
                emeraldCount,
                status.getGoal(),
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
                Material.DIAMOND_ORE,
                diamondCount,
                status.getGoal(),
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
                Material.COPPER_ORE,
                copperCount,
                status.getGoal(),
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
                Material.STONE,
                oreSum,
                status.getGoal(),
                status.getStage(),
                status.getColor()+"鉱石破壊数",
                new ArrayList<>(List.of(
                        (status.isAchieved() ? "":"Next: ")+oreSum+"/"+status.getGoal()
                ))
        );
    }
}
