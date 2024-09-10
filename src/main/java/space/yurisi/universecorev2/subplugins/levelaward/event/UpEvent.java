package space.yurisi.universecorev2.subplugins.levelaward.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.event.level.LevelUpEvent;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

public final class UpEvent implements Listener {

    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        Player player = event.getPlayer();
        int newLevel = event.getLevel();

        if (newLevel % 5 == 0 && newLevel % 100 != 0) {
            // 5レベルずつ上がった場合の処理をここに書く
            try {
                UniverseEconomyAPI.getInstance().addMoney(player,1000L);
            } catch (UserNotFoundException e) {
                player.sendMessage(Component.text("プレイヤーデータが存在しません。管理者に報告してください。コード-RWC1"));
            } catch (MoneyNotFoundException e) {
                player.sendMessage(Component.text("マネーデータが存在しません。管理者に報告してください。コード-RWC2"));
            } catch (CanNotAddMoneyException e) {
                player.sendMessage(Component.text("桁数が多すぎます。管理者に報告してください。コード-RWC3"));
            } catch (ParameterException e) {
                player.sendMessage(Component.text("マイナスの値を指定しています。管理者に報告してください。コードRWC4"));
            }
            return;


        }
        if (newLevel % 100 == 0) {
            player.sendMessage(Component.text("§aCONGRATULATIONS!"+newLevel+"levelを達成しました!"));
            Bukkit.getServer().broadcast(Component.text("§l"+player.getName()+"が"+newLevel+"levelを達成しました!"));
            player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        }
    }
}