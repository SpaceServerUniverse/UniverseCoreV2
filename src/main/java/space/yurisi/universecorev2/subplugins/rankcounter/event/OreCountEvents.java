package space.yurisi.universecorev2.subplugins.rankcounter.event;

import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;
import space.yurisi.universecorev2.database.models.count.OreCount;

public class OreCountEvents implements Listener {

    private final CounterModelManager manager;

    public OreCountEvents(CounterModelManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        OreCount oreCount = manager.get(player).getOreCount();

        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) {
            return;
        }

        switch (event.getBlock().getType()) {
            case COAL_ORE, DEEPSLATE_COAL_ORE -> oreCount.setCoal_ore(oreCount.getCoal_ore() + 1);
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE -> oreCount.setLapis_ore(oreCount.getLapis_ore() + 1);
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> oreCount.setRedstone_ore(oreCount.getRedstone_ore() + 1);
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE -> oreCount.setDiamond_ore(oreCount.getDiamond_ore() + 1);
            case EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> oreCount.setEmerald_ore(oreCount.getEmerald_ore() + 1);
            case IRON_ORE, DEEPSLATE_IRON_ORE -> oreCount.setIron_ore(oreCount.getIron_ore() + 1);
            case GOLD_ORE, DEEPSLATE_GOLD_ORE -> oreCount.setGold_ore(oreCount.getGold_ore() + 1);
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> oreCount.setCopper_ore(oreCount.getCopper_ore() + 1);
        }
    }
}
