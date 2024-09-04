package space.yurisi.universecorev2.subplugins.rankcounter.event;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;
import space.yurisi.universecore.utils.block_type.Flower;
import space.yurisi.universecorev2.database.models.count.LifeCount;
import space.yurisi.universecore.utils.block_type.Wood;

public class LifeCountEvents implements Listener {

    private CounterModelManager manager;

    public LifeCountEvents(CounterModelManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        LifeCount lifeCount = manager.get(player).getLifeCount();

        if (Wood.isWood(event.getBlock().getType())) {
            lifeCount.setWood_break(lifeCount.getWood_break() + 1);
        }

        lifeCount.setBlock_break(lifeCount.getBlock_break() + 1);
    }


    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) return;

        LifeCount lifeCount = manager.get(player).getLifeCount();
        if (Flower.isFlower(event.getBlock().getType())) {
            lifeCount.setFlower_place(lifeCount.getFlower_place() + 1);
        }

        lifeCount.setBlock_place(lifeCount.getBlock_place() + 1);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();

        LifeCount lifeCount = manager.get(player).getLifeCount();
        if(event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            lifeCount.setFishing(lifeCount.getFishing() + 1);
        }
    }
}
