package space.yurisi.universecorev2.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.menu.MainMenu;
import space.yurisi.universecorev2.utils.Message;

import java.util.Random;

public class menuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        MainMenu mainMenu = new MainMenu();
        mainMenu.sendMenu(player);

        // 0.122%の確率でクリーパーの効果音を再生
        if (new Random().nextDouble() < 0.000122) {
            player.playSound(player.getLocation(), Sound.ENTITY_CREEPER_PRIMED, 10, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
            Message.sendSuccessMessage(player, "[管理AI]", "§c§oメニューを開きました...ヮ! クリーパーが... (´・ω・`)");
        } else {
            player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_FALL, 10, 1);
            Message.sendSuccessMessage(player, "[管理AI]", "メニューを開きました");
        }

        return true;
    }
}
