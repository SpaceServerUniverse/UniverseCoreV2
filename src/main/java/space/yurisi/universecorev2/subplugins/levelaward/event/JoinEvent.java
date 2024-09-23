package space.yurisi.universecorev2.subplugins.levelaward.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.subplugins.levelaward.LevelAward;
import space.yurisi.universecorev2.utils.Message;

import java.io.IOException;

public final class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        try{
            LevelAward.getInstance().getConfig().register(event.getPlayer().getUniqueId().toString());
        }catch(IOException e){
            Message.sendErrorMessage(event.getPlayer(), "[じょいいべ（笑）]", "うぇらー、吐いてますよー（笑）");
        }
    }
}
