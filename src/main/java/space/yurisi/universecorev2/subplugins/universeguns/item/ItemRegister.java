package space.yurisi.universecorev2.subplugins.universeguns.item;

import space.yurisi.universecorev2.subplugins.universeguns.item.gun_item.R4C;
import space.yurisi.universecorev2.subplugins.universeguns.item.gun_item.RPG;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemRegister {

    private static HashMap<String, GunItem> items = new HashMap<>();
    private static ArrayList<String> itemNames = new ArrayList<>();
    public ItemRegister(){
        register();
    }

    public void register() {
        items.put(R4C.id, new R4C());
        items.put(RPG.id, new RPG());
        itemNames.add(R4C.id);
        itemNames.add(RPG.id);
    }

    public static GunItem getItem(String id){
        if(!items.containsKey(id)){
            return null;
        }
        return items.get(id);
    }

    public static Boolean isGun(String id){
        return itemNames.contains(id);
    }
}
