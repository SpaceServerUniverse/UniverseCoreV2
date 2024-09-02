package space.yurisi.universecorev2.subplugins.levelsystem.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.levelsystem.event.level.LevelUpEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode.ILevelMode;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode.LevelModes;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode.NormalLevelMode;

public class PlayerLevelData {

    private LevelModes levelMode;

    private Long total_exp;

    private int level;

    private Long exp;

    private Long level_mode_total_exp;

    private Long exp_for_next_level;

    private Player player;

    public PlayerLevelData(
            Player player,
            LevelModes levelMode,
            Long total_exp,
            int level,
            Long exp,
            Long level_mode_total_exp,
            Long exp_for_next_level
    ) {
        this.player = player;
        this.levelMode = levelMode;
        this.total_exp = total_exp;
        this.level = level;
        this.exp = exp;
        this.level_mode_total_exp = level_mode_total_exp;
        this.exp_for_next_level = exp_for_next_level;
    }

    public LevelModes getLevelMode() {
        return levelMode;
    }

    public void setLevelMode(LevelModes levelMode) {
        this.levelMode = levelMode;
    }

    public ILevelMode getLevelModeInterface() {
        if (getLevelMode() == LevelModes.NORMAL) {
            return new NormalLevelMode();
        }
        return null;
    }

    public Long getTotal_exp() {
        return total_exp;
    }

    public void setTotal_exp(Long total_exp) {
        this.total_exp = total_exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public Long getLevel_mode_total_exp() {
        return level_mode_total_exp;
    }

    public void setLevel_mode_total_exp(Long level_mode_total_exp) {
        this.level_mode_total_exp = level_mode_total_exp;
    }

    public Long getExp_for_next_level() {
        return exp_for_next_level;
    }

    public void setExp_for_next_level(Long exp_for_next_level) {
        this.exp_for_next_level = exp_for_next_level;
    }

    public Player getPlayer() {
        return this.player;
    }

    /**
     * 現在のレベルモードでの全ての経験値を取得
     *
     * @return long
     */
    public Long getLevelModeAllExp() {
        return level_mode_total_exp + exp_for_next_level;
    }

    /**
     * 次にレベルアップする際に必要な経験値を取得
     *
     * @return long
     */
    public Long getNextLevelExp() {
        return getLevelModeInterface().getLevelTable(level) + level_mode_total_exp;
    }

    public double getPercentage() {
        if (level == getLevelModeInterface().getMaxLevel()) {
            return 1;
        }

        if (getLevelModeAllExp() == 0) {
            return 0;
        }

        double a = getLevelModeAllExp() - level_mode_total_exp;
        double b = getLevelModeInterface().getLevelTable(level);

        return a / b;
    }

    public void addExp(int exp) {
        if(DayOfWeek.getInstance().isHoliday()){
            double temp = exp * 1.5;
            exp = (int) Math.floor(temp);
        }

        for (int i = 1; i <= exp; i++) {
            exp_for_next_level++;
            total_exp++;

            if (level >= getLevelModeInterface().getMaxLevel()) {
                continue;
            }

            if (exp_for_next_level >= getLevelModeInterface().getLevelTable(level)) {
                level_mode_total_exp += exp_for_next_level;
                exp_for_next_level = 0L;
                level++;
                Bukkit.getPluginManager().callEvent(new LevelUpEvent(player, level, levelMode));
            }
        }
    }
}
