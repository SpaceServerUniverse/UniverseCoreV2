package space.yurisi.universecorev2.subplugins.changemessages.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import space.yurisi.universecorev2.subplugins.changemessages.data.SuicidePlayerData;
import space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death.*;

import javax.annotation.Nullable;
import java.util.Optional;

public class DeathEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        if(SuicidePlayerData.getInstance().isSuicide(player)) {
            SuicidePlayerData.getInstance().setChoke(player, false);
            event.deathMessage(null);
            return;
        }
        Player killer = event.getEntity().getKiller();
        EntityDamageEvent.DamageCause cause = Optional.ofNullable(event.getEntity().getLastDamageCause())
                .map(EntityDamageEvent::getCause)
                .orElse(null);
        BasePlayerDeathEventMessage messageClass = getDeathMessageClass(player, killer, cause);
        event.deathMessage(messageClass.getMessage());
    }

    private BasePlayerDeathEventMessage getDeathMessageClass(Player player, @Nullable Player killer, @Nullable EntityDamageEvent.DamageCause cause) {
        switch (cause) {
            case ENTITY_ATTACK:
                return new EntityAttackBasePlayerDeathEventMessage(player, killer);
            case PROJECTILE:
                return new ProjectileBasePlayerDeathEventMessage(player, killer);
            case SUFFOCATION:
                return new SuffocationBasePlayerDeathEventMessage(player);
            case FALL:
                return new FallBasePlayerDeathEventMessage(player);
            case FIRE:
                return new FireBasePlayerDeathEventMessage(player);
            case FIRE_TICK:
                return new FireTickBasePlayerDeathEventMessage(player);
            case LAVA:
                return new LavaBasePlayerDeathEventMessage(player);
            case DROWNING:
                return new DrowningBasePlayerDeathEventMessage(player);
            case BLOCK_EXPLOSION:
                return new BlockExplosionBasePlayerDeathEventMessage(player);
            case ENTITY_EXPLOSION:
                return new EntityExplosionBasePlayerDeathEventMessage(player, killer);
            case VOID:
                return new VoidBasePlayerDeathEventMessage(player);
            case SUICIDE:
                return new SuicideBasePlayerDeathEventMessage(player);
            case MAGIC:
            default:
                return new DefaultBasePlayerDeathEventMessage(player);
        }
    }
}
