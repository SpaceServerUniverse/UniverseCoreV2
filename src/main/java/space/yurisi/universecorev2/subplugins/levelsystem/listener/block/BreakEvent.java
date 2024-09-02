package space.yurisi.universecorev2.subplugins.levelsystem.listener.block;

import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;

import java.util.Map;

public class BreakEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        if(event.isCancelled()){
            return;
        }

        Player player = event.getPlayer();
        LevelSystemAPI api = LevelSystemAPI.getInstance();

        if(!player.getGameMode().equals(GameMode.SURVIVAL)){
            return;
        }

        Map<Enchantment, Integer> main_hand_enchantment = player.getInventory().getItemInMainHand().getEnchantments();
        if (main_hand_enchantment.containsKey(Enchantment.SILK_TOUCH)){
            return;
        }

        try {
            switch (event.getBlock().getType()){
                case STONE, DEEPSLATE -> api.addExp(player, 3);
                case COAL_ORE, DEEPSLATE_COAL_ORE ->  api.addExp(player, 10);
                case LAPIS_ORE,DEEPSLATE_LAPIS_ORE, REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> api.addExp(player, 20);
                case DEEPSLATE_DIAMOND_ORE ->api.addExp(player, 100);
                case EMERALD_ORE, DEEPSLATE_EMERALD_ORE ->  api.addExp(player, 200);
                case DIAMOND_ORE -> api.addExp(player, 300);
                default -> api.addExp(player, 1);
            }
        } catch (PlayerDataNotFoundException ignored) {
        }
    }
}
