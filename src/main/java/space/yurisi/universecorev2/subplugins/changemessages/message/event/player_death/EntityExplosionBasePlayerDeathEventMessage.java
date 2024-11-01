package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityExplosionBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage{

    public EntityExplosionBasePlayerDeathEventMessage(Player player, Player killer) {
        super(player, killer);
    }

    @Override
    protected void init(Player player, Player killer) {
        if (killer == null) {
            EntityDamageEvent cause = player.getLastDamageCause();
            if (cause instanceof EntityDamageByEntityEvent e && e.getDamager() instanceof Projectile damager) {
                if (damager.getShooter() instanceof Entity shooter) {
                    String item = getItemName((Player) shooter);
                    setMessages(
                            new Component[]{
                                    Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で爆破しました"),
                                    Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で爆殺しました"),
                                    Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で絶命させた！"),
                                    Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で倒しました"),

                            }
                    );
                } else {
                    setMessages(
                            new Component[]{
                                    Component.text("§a§l[死亡管理AI] §c" + player.getName() + "§a が死亡した")
                            }
                    );
                }
                return;

            }

            setMessages(
                    new Component[]{
                            Component.text("")
                    }
            );
            return;

        }

        if (player.getName().equals(killer.getName())) {
            setMessages(
                    new Component[]{
                            Component.text("§a§l[死亡管理AI] §c" + player.getName() + "§a が 自分の放った矢で自爆した"),
                            Component.text("§a§l[死亡管理AI] §c" + player.getName() + "§a が 自分の放った弾で自爆した")
                    }
            );
            return;
        }

        String item = getItemName(killer);

        setMessages(
                new Component[]{
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- で爆殺しました"),
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- でばらばらにしました"),
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- の爆発によって生命活動が停止させた"),
                        Component.text("§a§l[戦闘型AI] §c" + killer.getName() + "§a が §b" + player.getName() + "§a を -§d" + item + "§a- の爆発で骨にした"),
                }
        );
    }
}
