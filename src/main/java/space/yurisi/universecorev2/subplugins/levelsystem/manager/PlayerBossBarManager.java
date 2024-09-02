package space.yurisi.universecorev2.subplugins.levelsystem.manager;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerBossBarManager {

    private HashMap<UUID, BossBar> data = new HashMap<>();

    public void register(Player player) {
        BossBar bossBar = Bukkit.createBossBar("", BarColor.PINK, BarStyle.SEGMENTED_10);
        bossBar.setVisible(true);
        bossBar.addPlayer(player);
        this.data.put(player.getUniqueId(), bossBar);
    }

    public BossBar get(Player player) {
        return this.data.get(player.getUniqueId());
    }

    public boolean exists(Player player) {
        return this.data.containsKey(player.getUniqueId());
    }

    public void unregister(Player player) {
        this.data.remove(player.getUniqueId());
    }

}
