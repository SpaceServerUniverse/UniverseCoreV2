package space.yurisi.universecorev2.subplugins.universeutilcommand.trash;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import space.yurisi.universecorev2.utils.Message;

public class TrashCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        Inventory trashInv = player.getServer().createInventory(null, 54, Component.text("ゴミ箱 (取り扱い注意)"));
        player.openInventory(trashInv);

        Message.sendSuccessMessage(player, "[ゴミ収集AI]", "ゴミ箱を開きました。捨てたアイテムは帰ってきません。取り扱いには十分注意してください。");

        return true ;
    }
}
