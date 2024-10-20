package space.yurisi.universecorev2.subplugins.autocollect.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AutoCollectMap {

    private static AutoCollectMap instance;
    private final Map<String, Boolean> autoCollectMap;

    public AutoCollectMap() {
        instance = this;
        this.autoCollectMap = new HashMap<>();
    }

    public static AutoCollectMap getInstance() {
        return instance;
    }

    public void toggleAutoCollect(Player player){
        this.autoCollectMap.put(player.getUniqueId().toString(),!this.autoCollectMap.get(player.getUniqueId().toString()));
    }

    public boolean isAutoCollect(Player player){
        return this.autoCollectMap.get(player.getUniqueId().toString());
    }

    public void init(Player player){
        this.autoCollectMap.put(player.getUniqueId().toString(),false);
    }
}
