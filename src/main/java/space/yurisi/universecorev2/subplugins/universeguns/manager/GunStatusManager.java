package space.yurisi.universecorev2.subplugins.universeguns.manager;

import space.yurisi.universecorev2.item.gun.Gun;
import space.yurisi.universecorev2.subplugins.universeguns.core.GunStatus;

import java.util.HashMap;
import java.util.Map;

public class GunStatusManager {
    private static Map<String, GunStatus> gunStatusMap = new HashMap<>();

    public static void register(String uuid, Gun gun){
        gunStatusMap.put(uuid, new GunStatus(gun));
    }

    public static boolean isExists(String uuid){
        return gunStatusMap.containsKey(uuid);
    }

    public static GunStatus get(String uuid){
        return gunStatusMap.get(uuid);
    }

    public static void remove(String uuid){
        gunStatusMap.remove(uuid);
    }
}
