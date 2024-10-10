package space.yurisi.universecorev2.subplugins.birthdaycard.event.player;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.utils.Message;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;
import java.util.UUID;

public class JoinEvent implements Listener {
    private BirthdayCardRepository birthdayCardRepository;

    public JoinEvent() {
        birthdayCardRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getBirthdayCardRepository();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<BirthdayData> birthdayDataList = null;
        try {
            birthdayDataList = birthdayCardRepository.getAllBirthdayData();
        } catch (BirthdayDataNotFoundException ignored) {
            return;
        }
        for (BirthdayData birthdayData : birthdayDataList) {
            MonthDay birthday = MonthDay.of(birthdayData.getMonth(), birthdayData.getDay());
            LocalDate thisYearBirthday = LocalDate.of(LocalDate.now().getYear(), birthday.getDayOfMonth(), birthday.getMonthValue());
            LocalDate today = LocalDate.now();
            LocalDate tenDaysLater = LocalDate.now().plusDays(10);
            if (thisYearBirthday.isEqual(today)) {
                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "今日はあなたの誕生日です！お祝いしましょう！");
                return;
            }
            if (thisYearBirthday.isAfter(today) && thisYearBirthday.isBefore(tenDaysLater)) {

                UUID playerUUID = UUID.fromString(birthdayData.getUuid());
                if(!player.getUniqueId().equals(playerUUID)) {
                    OfflinePlayer offlineBirthdayPlayer = Bukkit.getOfflinePlayer(playerUUID);
                    String playerName = offlineBirthdayPlayer.getName() != null ? offlineBirthdayPlayer.getName() : "不明なプレイヤー";
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, playerName + "の誕生日が近いよ！誕生日カードを書かない？");
                    return;
                }
            }
        }
    }
}
