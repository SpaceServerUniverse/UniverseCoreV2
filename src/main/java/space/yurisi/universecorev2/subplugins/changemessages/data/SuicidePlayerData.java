package space.yurisi.universecorev2.subplugins.changemessages.data;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class SuicidePlayerData {

    private static SuicidePlayerData instance;
    private Map<String, Boolean> map;

    public SuicidePlayerData(){
        instance = this;
        map = new HashMap<>();
    }

    public static SuicidePlayerData getInstance(){
        return instance;
    }

    public void register(Player player){
        map.put(player.getUniqueId().toString(), false);
    }

    public void unregister(Player player){
        map.remove(player.getUniqueId().toString());
    }

    public void setChoke(Player player, boolean bool){
        map.put(player.getUniqueId().toString(), bool);
    }

    public boolean isSuicide(Player player){
        return map.get(player.getUniqueId().toString());
    }
}
