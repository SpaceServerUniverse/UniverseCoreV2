package space.yurisi.universecorev2.subplugins.levelaward.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.event.level.LevelUpEvent;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.Message;

public final class UpEvent implements Listener {

    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        Player player = event.getPlayer();
        int newLevel = event.getLevel();

        if (newLevel % 5 == 0 && newLevel % 100 != 0) {
            // 5レベルずつ上がった場合の処理をここに書く
            try {
                UniverseEconomyAPI.getInstance().addMoney(player,1000L, "レベルアップ報酬");
            } catch (UserNotFoundException e) {
                Message.sendErrorMessage(player, "[銀行管理AI]", "プレイヤーデータが存在しません。管理者に報告してください");
            } catch (MoneyNotFoundException e) {
                Message.sendErrorMessage(player, "[銀行管理AI]", "マネーデータが存在しません。管理者に報告してください");
            } catch (CanNotAddMoneyException e) {
                Message.sendErrorMessage(player, "[銀行管理AI]", "桁数が多すぎます。管理者に報告してください");
            } catch (ParameterException e) {
                Message.sendErrorMessage(player, "[銀行管理AI]", "マイナスの値を指定しています。管理者に報告してください");
            }
            return;
        }

        switch(newLevel){
            case 100:
                player.sendMessage(Component.text("§l§fCONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§f"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 200:
                player.sendMessage(Component.text("§l§aCONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§a"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 300:
                player.sendMessage(Component.text("§l§2CONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§2"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 400:
                player.sendMessage(Component.text("§l§9CONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§9"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 500:
                player.sendMessage(Component.text("§l§bCONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§b"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 600:
                player.sendMessage(Component.text("§l§eCONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§e"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 700:
                player.sendMessage(Component.text("§l§6CONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§6"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 800:
                player.sendMessage(Component.text("§l§cCONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§c"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 900:
                player.sendMessage(Component.text("§l§4CONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§4"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
            case 1000:
                player.sendMessage(Component.text("§l§0CONGRATULATIONS!"+newLevel+"levelを達成しました!"));
                Bukkit.getServer().broadcast(Component.text("§l§0"+player.getName()+"が"+newLevel+"levelを達成しました!"));
                break;
        }


        if (newLevel % 100 == 0) {
            player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        }
    }
}
