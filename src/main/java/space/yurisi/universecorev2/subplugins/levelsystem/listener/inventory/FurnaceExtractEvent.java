package space.yurisi.universecorev2.subplugins.levelsystem.listener.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;

public class FurnaceExtractEvent implements Listener {

    @EventHandler
    public void onFurnaceExtract(org.bukkit.event.inventory.FurnaceExtractEvent event){
        Player player = event.getPlayer();
        Material material = event.getItemType();
        int amount = event.getItemAmount();
        LevelSystemAPI api = LevelSystemAPI.getInstance();

        try {
            switch (material){
                case COPPER_INGOT -> api.addExp(player, 10 * amount);
                case IRON_INGOT -> api.addExp(player, 5 * amount);
                case GOLD_INGOT -> api.addExp(player, 8 * amount);
                case NETHERITE_SCRAP -> api.addExp(player, 50 * amount);
            }
        } catch (PlayerDataNotFoundException ignored) {
        }
    }
}
