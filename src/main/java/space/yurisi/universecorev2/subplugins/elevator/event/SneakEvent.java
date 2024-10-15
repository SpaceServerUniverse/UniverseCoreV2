package space.yurisi.universecorev2.subplugins.elevator.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;

public class SneakEvent implements Listener {
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event){
        if(!event.isSneaking()){
            return;
        }

        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (location.getBlock().getType() != Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
            return;
        }

        HashMap<Integer, Location> locations = new HashMap<>();

        int floor = 0;
        int now = 0;
        Location addYLocation = location.clone();

        for (int y = location.getWorld().getMinHeight(); y <= location.getWorld().getMaxHeight(); y++) {
            addYLocation.setY(y);
            World world = Bukkit.getWorld(addYLocation.getWorld().getUID());
            if (world != null && world.getBlockAt(addYLocation).getType() != Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                continue;
            }

            floor++;
            locations.put(floor, addYLocation.clone());

            if ((int) Math.floor(location.getY()) >= y) {
                now++;
            }
        }

        int next = now - 1;

        if (!locations.containsKey(next)) {
            player.sendActionBar(Component.text("[エレベーター] §c既に最下層です。"));
            return;
        }

        player.teleport(locations.get(next));
        player.sendActionBar(Component.text("[エレベーター]" + floor + "階中§a" + next + "階§rにワープしました。"));
    }
}
