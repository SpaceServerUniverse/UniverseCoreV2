package space.yurisi.universecorev2.subplugins.containerprotect.manager;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LockManager {

    public static final int LOCK = 1;
    public static final int UNLOCK = 1 << 1;

    private final Map<UUID, Integer> playerStates = new HashMap<>();

    public boolean hasFlag(Player player, int flag) {
        Integer flags = playerStates.getOrDefault(player.getUniqueId(), 0);
        return (flags & flag) != 0;
    }

    public void setFlag(Player player, int flag) {
        playerStates.put(player.getUniqueId(), flag);
    }

    public void removeFlag(Player player) {
        playerStates.put(player.getUniqueId(), 0);
    }
}
