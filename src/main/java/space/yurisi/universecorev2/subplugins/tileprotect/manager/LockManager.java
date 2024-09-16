package space.yurisi.universecorev2.subplugins.tileprotect.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LockManager {

    private List<UUID> playerList = new ArrayList();

    public void setState(Player player){
        playerList.add(player.getUniqueId());
    }

    public Boolean isSetState(Player player){
        return playerList.contains(player.getUniqueId());
    }

    public boolean deleteState(Player player){playerList.remove(player.getUniqueId());
        return false;
    }


}