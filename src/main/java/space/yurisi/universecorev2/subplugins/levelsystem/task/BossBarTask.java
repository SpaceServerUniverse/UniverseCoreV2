package space.yurisi.universecorev2.subplugins.levelsystem.task;

import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.DayOfWeek;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.PlayerLevelData;

public class BossBarTask extends BukkitRunnable {

    private BossBar bossBar;

    private PlayerLevelData levelData;

    public BossBarTask(PlayerLevelData levelData, BossBar bossBar) {
        this.bossBar = bossBar;
        this.levelData = levelData;
    }

    @Override
    public void run() {
        String level = levelData.getLevel() == levelData.getLevelModeInterface().getMaxLevel() ?
                       "§e☆MAX☆" :
                       String.valueOf(levelData.getLevel());

        String bonus_message = DayOfWeek.getInstance().isHoliday() ? "§a休日ボーナス 1.5倍！" : "";
        this.bossBar.setTitle("レベル:" + level + " " + levelData.getLevelModeAllExp() + "/" + levelData.getNextLevelExp() + " " + bonus_message);
        this.bossBar.setProgress(levelData.getPercentage());
    }
}
