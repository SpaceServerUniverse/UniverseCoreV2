package space.yurisi.universecorev2.subplugins.levelsystem.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.solar_system.SolarSystemHead;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.subplugins.gacha.Gacha;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.manager.PlayerLevelDataManager;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.DayOfWeek;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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

        if (shouldGiveExp(tick)) {
            forEachOnlinePlayer(this::giveDailyExp);
        }

        if (shouldGiveTicket(tick)) {
            forEachOnlinePlayer(this::giveGachaTicket);
            tick = 1;
        }
    }

    private boolean shouldGiveExp(int tick) {
        return tick % 4 == 2;
    }

    private boolean shouldGiveTicket(int tick) {
        return tick == 8;
    }

    private void forEachOnlinePlayer(java.util.function.Consumer<Player> action) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            action.accept(player);
        }
    }

    private void giveDailyExp(Player player) {
        try {
            LevelSystemAPI.getInstance().addExp(player, 1000);
            Message.sendSuccessMessage(player, "[管理AI]", "遊んでくれてありがとうございます！1000EXPプレゼントです！");
        } catch (PlayerDataNotFoundException ignored) {
            // 必要ならログ出す: Bukkit.getLogger().fine(...)
        }
    }

    private void giveGachaTicket(Player player) {
        ItemStack ticket = UniverseItem.getItem(GachaTicket.id).getItem();
        Date expireAt = endOfDayPlusDays(2, ZoneId.of("Asia/Tokyo"));

        ReceiveBoxAPI.AddReceiveItem(ticket, player.getUniqueId(), expireAt, "継続ガチャチケット");
        Message.sendSuccessMessage(player, "[管理AI]", "遊んでくれてありがとうございます！ガチャチケットプレゼントします！受け取りボックスから受け取って下さい！");
    }

    private Date endOfDayPlusDays(int plusDays, ZoneId zoneId) {
        LocalDateTime end = LocalDateTime.now()
                .truncatedTo(ChronoUnit.DAYS)
                .plusDays(plusDays)
                .minusSeconds(1);

        return Date.from(end.atZone(zoneId).toInstant());
    }
}
