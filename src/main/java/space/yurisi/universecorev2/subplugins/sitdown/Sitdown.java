package space.yurisi.universecorev2.subplugins.sitdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.sitdown.event.EventListener;

import java.util.HashMap;
import java.util.UUID;


public final class Sitdown implements SubPlugin {

    private final HashMap<UUID, Entity> vehicles = new HashMap<>();

    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new EventListener(this), core);
    }

    public void setVehicle(Player player, Entity entity){
        this.vehicles.put(player.getUniqueId(), entity);
    }

    public Entity getVehicle(Player player){
        return this.vehicles.get(player.getUniqueId());
    }

    public void removeVehicle(Player player){
        this.vehicles.remove(player.getUniqueId());
    }

    public boolean existsVehicle(Player player){
        return this.vehicles.containsKey(player.getUniqueId());
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "sitdown";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}