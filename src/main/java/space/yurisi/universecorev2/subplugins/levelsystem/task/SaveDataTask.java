package space.yurisi.universecorev2.subplugins.levelsystem.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.manager.PlayerLevelDataManager;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.DayOfWeek;
import space.yurisi.universecorev2.utils.Message;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;

public class SaveDataTask extends BukkitRunnable {

    private PlayerLevelDataManager manager;

    private int tick = 1;

    public SaveDataTask(PlayerLevelDataManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.saveAll();
        DayOfWeek.getInstance().checkHoliday();
        tick++;
        if (tick == 4) {
            Collection<? extends Player> online = Bukkit.getServer().getOnlinePlayers();
            for (Player player : online) {
                try {
                    LevelSystemAPI.getInstance().addExp(player, 1000);
                    Message.sendSuccessMessage(player, "[管理AI]", "遊んでくれてありがとうございます！1000EXPプレゼントです！");
                } catch (PlayerDataNotFoundException ignored) {
                }
            }
            tick = 1;
        }
    }
}
