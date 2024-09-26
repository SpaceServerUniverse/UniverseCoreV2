package space.yurisi.universecorev2.subplugins.chestshop.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import space.yurisi.universecorev2.subplugins.chestshop.utils.SuperMessageHelper;

public class ItemNameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SuperMessageHelper.getSuccessMessage("このコマンドはゲーム内で実行してください"));
            return false;
        }
        PlayerInventory inventory = ((Player) sender).getInventory();
        ItemStack item = inventory.getItemInMainHand();
        if (item == null) {
            sender.sendMessage(SuperMessageHelper.getSuccessMessage("メインハンドに持ったアイテムの名前を表示します"));
            return false;
        } else {
            sender.sendMessage(SuperMessageHelper.getSuccessMessage("§c" + item.getType()));
            return true;
        }
    }
}
