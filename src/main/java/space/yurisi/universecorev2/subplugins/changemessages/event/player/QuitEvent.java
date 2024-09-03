package space.yurisi.universecorev2.subplugins.changemessages.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerQuitEvent.QuitReason reason = event.getReason();
        event.quitMessage(getComponent(player, reason));
    }

    private Component getComponent(Player player, PlayerQuitEvent.QuitReason reason) {
        String reason_ja;
        switch (reason) {
            case KICKED:
                reason_ja = "キック";
                break;
            case DISCONNECTED:
                reason_ja = "サーバー抜け";
                break;
            case TIMED_OUT:
                reason_ja = "タイムアウト";
                break;
            case ERRONEOUS_STATE:
                reason_ja = "エラー";
                break;
            default:
                reason_ja = reason.toString();
        }
        return Component.text("§7[退室] §c" + player.getName() + "§e様が§a" + reason_ja + "§eで§7§lオフライン§r§eになりました");
    }
}
