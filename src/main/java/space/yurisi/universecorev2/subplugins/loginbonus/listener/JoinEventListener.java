package space.yurisi.universecorev2.subplugins.loginbonus.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.LoginBonus;
import space.yurisi.universecorev2.exception.LoginBonusNotFoundException;
import space.yurisi.universecorev2.utils.DateHelper;

import java.util.Date;

public class JoinEventListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Date date = DateHelper.today();
        try {
            LoginBonus loginBonus = UniverseCoreV2API.getInstance().getDatabaseManager().getLoginBonusRepository().getLoginBonusByPlayerAndDate(player, date);
            if(loginBonus.getIs_received()){
                return;
            }
        } catch (LoginBonusNotFoundException e) {
        }

        Component component = Component.text("本日のログインボーナスが受け取れます！§l§n[ここをクリックで受取]")
                .clickEvent(ClickEvent.runCommand("/loginbonus"))
                .hoverEvent(HoverEvent.showText(Component.text("クリックで受取")));
        player.sendMessage(component);

    }

}
