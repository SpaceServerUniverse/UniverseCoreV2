package space.yurisi.universecorev2.subplugins.levelsystem.event.level;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.levelsystem.event.LevelSystemEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode.LevelModes;

public class LevelUpEvent extends LevelSystemEvent {

    private Player player;

    private int level;

    private LevelModes levelMode;

    public LevelUpEvent(Player player, int level, LevelModes levelMode) {
        this.player = player;
        this.level = level;
        this.levelMode = levelMode;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLevel() {
        return level;
    }

    public LevelModes getLevelMode() {
        return levelMode;
    }
}
