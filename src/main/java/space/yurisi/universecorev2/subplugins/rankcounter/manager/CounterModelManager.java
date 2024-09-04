package space.yurisi.universecorev2.subplugins.rankcounter.manager;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.rankcounter.utils.Counters;

import java.util.HashMap;
import java.util.UUID;

public class CounterModelManager {

    private final HashMap<UUID, Counters> countersHashMap = new HashMap<>();

    public void register(Player player, Counters counters){
        this.countersHashMap.put(player.getUniqueId(), counters);
    }

    public boolean exists(Player player){
        return this.countersHashMap.containsKey(player.getUniqueId());
    }

    public Counters get(Player player){
        return this.countersHashMap.get(player.getUniqueId());
    }

    public void unregister(Player player){
        this.countersHashMap.remove(player.getUniqueId());
    }
}
