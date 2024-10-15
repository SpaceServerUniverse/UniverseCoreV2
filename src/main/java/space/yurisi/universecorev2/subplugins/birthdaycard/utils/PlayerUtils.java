package space.yurisi.universecorev2.subplugins.birthdaycard.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlayerUtils {
    public static String getPlayerNameByUuid(UUID playerUuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerUuid);
        return offlinePlayer.getName() != null ? offlinePlayer.getName() : "不明なプレイヤー";
    }
}
