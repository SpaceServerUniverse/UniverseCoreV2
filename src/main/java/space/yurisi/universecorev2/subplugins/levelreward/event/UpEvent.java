package space.yurisi.universecorev2.subplugins.levelreward.event;

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

import java.util.Map;

public final class UpEvent implements Listener {

    private static final Map<Integer, String> LEVEL_COLOR = Map.of(
            100, "§f",
            200, "§a",
            300, "§2",
            400, "§9",
            500, "§b",
            600, "§e",
            700, "§6",
            800, "§c",
            900, "§4",
            1000, "§0"
    );

    @EventHandler
    public void onLevelUp(LevelUpEvent event) {
        Player player = event.getPlayer();
        int level = event.getLevel();

        if (level % 5 == 0 && level % 100 != 0) {
            giveLevelRewardMoney(player, 1000L);
            return;
        }

        if (level % 100 == 0) {
            announceHundredsLevel(player, level);
            player.getWorld().playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        }
    }

    private void giveLevelRewardMoney(Player player, long amount) {
        try {
            UniverseEconomyAPI.getInstance().addMoney(player, amount, "レベルアップ報酬");
        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, "[銀行管理AI]", "プレイヤーデータが存在しません。管理者に報告してください");
        } catch (MoneyNotFoundException e) {
            Message.sendErrorMessage(player, "[銀行管理AI]", "マネーデータが存在しません。管理者に報告してください");
        } catch (CanNotAddMoneyException e) {
            Message.sendErrorMessage(player, "[銀行管理AI]", "桁数が多すぎます。管理者に報告してください");
        } catch (ParameterException e) {
            Message.sendErrorMessage(player, "[銀行管理AI]", "マイナスの値を指定しています。管理者に報告してください");
        }
    }

    private void announceHundredsLevel(Player player, int level) {
        String color = LEVEL_COLOR.getOrDefault(level, "§f");
        String congrats = "§l" + color + "CONGRATULATIONS!" + level + "levelを達成しました!";
        String broadcast = "§l" + color + player.getName() + "が" + level + "levelを達成しました!";

        player.sendMessage(Component.text(congrats));
        Bukkit.getServer().broadcast(Component.text(broadcast));
    }

}
