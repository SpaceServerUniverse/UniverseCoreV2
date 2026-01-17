package space.yurisi.universecorev2.subplugins.birthdaycard.event.player;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
    private final BirthdayCardRepository birthdayCardRepository;

    public JoinEvent() {
        birthdayCardRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        List<BirthdayData> birthdayDataList;
        birthdayDataList = birthdayCardRepository.getAllBirthdayData();
        for (BirthdayData birthdayData : birthdayDataList) {
            if (birthdayData.isGiftReceived()) {
                continue;
            }
            MonthDay birthday = MonthDay.of(birthdayData.getMonth(), birthdayData.getDay());
            LocalDate thisYearBirthday = LocalDate.of(LocalDate.now().getYear(), birthday.getMonthValue(), birthday.getDayOfMonth());
            LocalDate today = LocalDate.now();
            LocalDate tenDaysLater = LocalDate.now().plusDays(10);
            if (thisYearBirthday.isEqual(today)) {
                if (birthdayData.getUuid().equals(player.getUniqueId().toString())) {
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "今日はあなたの誕生日です！");
                    player.performCommand("birthday gift");
                    return;
                }
            }
            if ((thisYearBirthday.isEqual(today) || thisYearBirthday.isAfter(today)) && thisYearBirthday.isBefore(tenDaysLater.plusDays(1))) {
                UUID playerUUID = UUID.fromString(birthdayData.getUuid());
                if (!playerUUID.equals(player.getUniqueId())) {
                    OfflinePlayer offlineBirthdayPlayer = Bukkit.getOfflinePlayer(playerUUID);
                    String playerName = offlineBirthdayPlayer.getName() != null ? offlineBirthdayPlayer.getName() : "不明なプレイヤー";
                    Message.sendSuccessMessage(player, BirthdayCard.PREFIX, playerName + "の誕生日が近いよ！誕生日カードを書かない？");
                    Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[誕生日カードを書く]", ClickEvent.runCommand("/birthday get " + playerName), playerName + "の誕生日カードを取得する");
                    return;
                }
            }

        }
    }
}
