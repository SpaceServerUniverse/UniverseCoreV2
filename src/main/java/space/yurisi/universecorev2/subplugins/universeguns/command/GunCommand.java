package space.yurisi.universecorev2.subplugins.universeguns.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.menu.AmmoManagerInventoryMenu;

public class GunCommand implements CommandExecutor {

    private final UniverseCoreAPIConnector connector;

    public GunCommand(UniverseCoreAPIConnector connector) {
        this.connector = connector;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        player.playSound(player.getLocation(), Sound.BLOCK_BARREL_OPEN, 1, 1);

        AmmoManagerInventoryMenu ammoManagerInventoryMenu = new AmmoManagerInventoryMenu(connector);
        ammoManagerInventoryMenu.sendMenu(player);

//        Message.sendSuccessMessage(player, "[武器AI]", "弾薬管理メニューを開きました");
        return true;
    }
}
