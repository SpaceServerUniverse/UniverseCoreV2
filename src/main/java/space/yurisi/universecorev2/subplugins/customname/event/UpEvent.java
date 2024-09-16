package space.yurisi.universecorev2.subplugins.customname.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import space.yurisi.universecorev2.subplugins.levelsystem.event.level.LevelUpEvent;
import space.yurisi.universecorev2.utils.PlayerState;

public class UpEvent implements Listener {

    public void onUp(LevelUpEvent event){
        Player player = event.getPlayer();
        PlayerState.setDisplayName(player);
    }
}
