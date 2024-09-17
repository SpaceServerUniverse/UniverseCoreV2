package space.yurisi.universecorev2.subplugins.containerprotect.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LockManager {

    private final List<UUID> playerList = new ArrayList<>();

    public void setState(Player player) {
        playerList.add(player.getUniqueId());
    }

    public Boolean isSetState(Player player) {
        return playerList.contains(player.getUniqueId());
    }

    public void deleteState(Player player) {//FIXME: deleteなのに内部ではremove
        playerList.remove(player.getUniqueId());
    }
}