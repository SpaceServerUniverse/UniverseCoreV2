package space.yurisi.universecorev2.subplugins.changemessages.message.event.player_death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public final class ProjectileBasePlayerDeathEventMessage extends BasePlayerDeathEventMessage {
    public ProjectileBasePlayerDeathEventMessage(Player player, Player killer) {
        super(player, killer);
    }


    @Override
    protected void init(Player player, Player killer) {
        if (killer == null) {
            EntityDamageEvent cause = player.getLastDamageCause();
            if (cause instanceof EntityDamageByEntityEvent e && e.getDamager() instanceof Projectile damager) {

                Object shooterObj = damager.getShooter();

                if (shooterObj instanceof Entity shooter) {
                    if (shooter instanceof Player shooterPlayer && shooterPlayer.getUniqueId().equals(player.getUniqueId())) {
                        setDefaultMessages(player);
                        return;
                    }
                    String item = "";

                    if (shooter instanceof Player shooterPlayer) {
                        item = getItemName(shooterPlayer);
                    } else if (shooter instanceof LivingEntity) {
                        item = "弓";
                    } else {
                        item = "不明";
                    }

                    String itemPart = item.isBlank() ? "" : " -§d" + item + "§a-";

                    setMessages(new Component[]{
                            Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を" + itemPart + " で射撃しました"),
                            Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を" + itemPart + " で打ち抜きました"),
                            Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を" + itemPart + " で絶命させた！"),
                            Component.text("§a§l[戦闘型AI] §c" + shooter.getName() + "§a が §b" + player.getName() + "§a を" + itemPart + " で倒しました"),
                    });
                    return;
                }

                setDefaultMessages(player);
                return;
            }

            setDefaultMessages(player);
        } else {
            setMessages(
                    new Component[]{
                            Component.text("§a§l[死亡管理AI] §c" + player.getName() + "§a が死亡した")
                    }
            );
        }

    }

    private void setDefaultMessages(Player player) {
        setMessages(new Component[]{
                Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a はなぞのダメージにより死んだ"),
                Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a はなにかの力によって死亡した"),
                Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a はなにかの圧力によって死亡した"),
                Component.text("§a§l[死亡管理AI] §b" + player.getName() + "§a は神の力によって消滅した")
        });
    }
}
