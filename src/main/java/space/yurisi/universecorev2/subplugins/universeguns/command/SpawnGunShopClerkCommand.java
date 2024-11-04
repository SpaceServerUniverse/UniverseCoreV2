package space.yurisi.universecorev2.subplugins.universeguns.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.api.LuckPermsWrapper;
import space.yurisi.universecorev2.subplugins.universeguns.constants.ShopType;
import space.yurisi.universecorev2.subplugins.universeguns.entity.GunShopClerk;
import space.yurisi.universecorev2.utils.Message;

public class SpawnGunShopClerkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player player)) {
            return false;
        }

        if (!LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
            Message.sendErrorMessage(player, "[管理AI]", "このコマンドを実行する権限がありません。");
            return false;
        }

        if(args == null || args.length < 1) {
            Message.sendErrorMessage(player, "[武器AI]", "ショップの種類を指定してください。");
            Message.sendErrorMessage(player, "[武器AI]", "hg | smg | sg");
            Message.sendErrorMessage(player, "[武器AI]", "ar | sr");
            Message.sendErrorMessage(player, "[武器AI]", "lmg | ex |　armor");
            return false;
        }

        String strShopType = args[0];
        for(ShopType shopType : ShopType.values()) {
            if(shopType.getName().equals(strShopType)) {
                GunShopClerk gunShopClerk = new GunShopClerk();
                gunShopClerk.spawnClerk(player.getLocation(), shopType);
                return true;
            }
        }

        Message.sendWarningMessage(player, "[武器AI]", "指定されたショップが見つかりませんでした。");

        return false;
    }
}
