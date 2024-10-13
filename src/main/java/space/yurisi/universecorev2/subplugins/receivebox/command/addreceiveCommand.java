package space.yurisi.universecorev2.subplugins.receivebox.command;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.ReceiveBox;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addreceiveCommand  implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        if(args.length < 3){
            Message.sendErrorMessage(player, "[管理AI]", "/addreceive player description expire_datetime([YYYY-mm-dd][HH:ii:ss])");
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.getType() == Material.AIR){
            Message.sendErrorMessage(player, "[管理AI]", "渡したいアイテムを手に持って下さい。");
            return false;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayerIfCached(args[0]);
        if(target == null){
            Message.sendErrorMessage(player, "[管理AI]", "プレイヤーが存在しません。");
            return false;
        }

        // 日付フォーマットを指定
        SimpleDateFormat formatter = new SimpleDateFormat("[yyyy-MM-dd][HH:mm:ss]");

        try {
            Date date = formatter.parse(args[2]);


            ReceiveBoxAPI.AddReceiveItem(item, target.getUniqueId(), date ,args[1]);
            Message.sendSuccessMessage(player, "[管理AI]", "追加しました！");
        } catch (ParseException e) {
            Message.sendErrorMessage(player, "[管理AI]", "日時のフォーマットが違います。");
        }

        return true;
    }
}
