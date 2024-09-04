package space.yurisi.universecorev2.subplugins.rankcounter.event;

import org.bukkit.GameMode;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wither;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;
import space.yurisi.universecorev2.database.models.count.KillDeathCount;

public class KillDeathCountEvents implements Listener {

    private final CounterModelManager manager;

    public KillDeathCountEvents(CounterModelManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player killer)) {
            return;
        }

        if (killer.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        KillDeathCount killDeathCount = manager.get(killer).getKillDeathCount();

        if (event.getEntity() instanceof Player player) {
            double health = player.getHealth() - event.getFinalDamage();
            if (health <= 0) {
                killDeathCount.setPlayer_kill(killDeathCount.getPlayer_kill() + 1);
            }

        }

        if (event.getEntity() instanceof Mob mob) {
            double health = mob.getHealth() - event.getFinalDamage();
            if (health <= 0) {
                killDeathCount.setMob_kill(killDeathCount.getMob_kill() + 1);
            }
        }

        if (event.getEntity() instanceof EnderDragon mob) {
            double health = mob.getHealth() - event.getFinalDamage();
            if (health <= 0) {
                killDeathCount.setEnder_dragon_kill(killDeathCount.getEnder_dragon_kill() + 1);
            }
        }

        if (event.getEntity() instanceof Wither mob) {
            double health = mob.getHealth() - event.getFinalDamage();
            if (health <= 0) {
                killDeathCount.setWither_kill(killDeathCount.getWither_kill() + 1);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }

        KillDeathCount killDeathCount = manager.get(player).getKillDeathCount();
        killDeathCount.setDeath(killDeathCount.getDeath() + 1);
    }
}
