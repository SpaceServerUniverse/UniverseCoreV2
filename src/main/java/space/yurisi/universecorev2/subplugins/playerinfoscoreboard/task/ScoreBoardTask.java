package space.yurisi.universecorev2.subplugins.playerinfoscoreboard.task;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

public final class ScoreBoardTask extends BukkitRunnable {

    private final Player player;

    private int tick = 1;

    private Long coin;

    public ScoreBoardTask(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        Scoreboard scoreboard = createScoreboard();
        Objective objective = scoreboard.getObjective("score");
        setMoney(objective, 10);
        setLocation(objective, 9);
        setWorld(objective, 8);
        setTime(objective, 7);
        //blank
        Objects.requireNonNull(objective).getScore("§r　").setScore(6);
        setOnline(objective, 5);
        setJob(objective, 4);
        setSpaceShipPoint(objective, 4);
        player.setScoreboard(scoreboard);
        changeTick();
    }

    private Scoreboard createScoreboard() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective o = scoreboard.registerNewObjective("score", Criteria.DUMMY, Component.text(this.getTickTitle()));
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        return scoreboard;
    }

    private void setMoney(Objective objective, int score) {
        UniverseEconomyAPI economyAPI = UniverseEconomyAPI.getInstance();
        String unit = economyAPI.getUnit();
        //40tickに1回だけ更新
        if (coin == null || tick % 4 == 0) {
            try {
                coin = economyAPI.getMoney(this.player);
            } catch (UserNotFoundException e) {
                Objects.requireNonNull(objective).getScore("§e所持金: ERROR1").setScore(score);
                return;
            } catch (MoneyNotFoundException e) {
                Objects.requireNonNull(objective).getScore("§e所持金: ERROR2").setScore(score);
                return;
            }
        }
        Objects.requireNonNull(objective).getScore("§e所持金: " + coin + unit).setScore(score);
    }

    private void setLocation(Objective objective, int score) {
        Location location = this.player.getLocation();
        int x = (int) Math.floor(location.getX());
        int y = (int) Math.floor(location.getY());
        int z = (int) Math.floor(location.getZ());
        Objects.requireNonNull(objective).getScore("§b座標: " + x + "," + y + "," + z).setScore(score);
    }

    private void setTime(Objective objective, int score) {
        LocalTime now = LocalTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);
        Objects.requireNonNull(objective).getScore("§c現在時刻: " + formattedTime).setScore(score);
    }

    private void setWorld(Objective objective, int score) {
        String world_name = this.player.getWorld().getName();
        Objects.requireNonNull(objective).getScore("§bワールド: " + world_name).setScore(score);
    }

    private void setJob(Objective objective, int score) {
        //TODO
        Objects.requireNonNull(objective).getScore("§6仕事: 無職").setScore(score);
    }

    private void setOnline(Objective objective, int score) {
        int online = Bukkit.getServer().getOnlinePlayers().size();
        int max = Bukkit.getServer().getMaxPlayers();
        Objects.requireNonNull(objective).getScore("§6オンライン人数: " + online + "/" + max).setScore(score);
    }

    private void setSpaceShipPoint(Objective objective, int score) {
        int online = Bukkit.getServer().getOnlinePlayers().size();
        int max = Bukkit.getServer().getMaxPlayers();
        Objects.requireNonNull(objective).getScore("§c宇宙船ポイント: 1000p").setScore(score);
    }

    private String getTickTitle() {
        return switch (tick) {
            case 5 -> "§f§l§e§lU§f§lNIVERSE";
            case 6 -> "§f§lU§e§lN§f§lIVERSE";
            case 7 -> "§f§lUN§e§lI§f§lVERSE";
            case 8 -> "§f§lUNI§e§lV§f§lERSE";
            case 9 -> "§f§lUNIV§e§lE§f§lRSE";
            case 10 -> "§f§lUNIVE§e§lR§f§lSE";
            case 11 -> "§f§lUNIVER§e§lS§f§lE";
            case 12 -> "§f§lUNIVERS§e§lE§f§l";
            case 13, 15 -> "§e§lUNIVERSE";
            case 14, 16 -> "§f§lUNIVERSE";
            default -> "§b§lSPACE§e§lSERVER";
        };
    }

    private void changeTick() {
        if (tick == 16) {
            this.tick = 0;
            return;
        }
        this.tick++;
    }
}
