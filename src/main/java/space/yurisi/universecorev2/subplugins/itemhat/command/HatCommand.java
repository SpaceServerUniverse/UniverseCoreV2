package space.yurisi.universecorev2.subplugins.itemhat.command;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class HatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("このコマンドはゲーム内で実行してください"));
            return false;
        }

        PlayerInventory inventory = player.getInventory();

        ItemStack item = inventory.getItemInMainHand().clone();
        ItemStack hat = inventory.getHelmet();
        if(hat == null){
            hat = new ItemStack(Material.AIR);
        }else{
            hat = hat.clone();
        }

        inventory.setHelmet(item);
        inventory.setItemInMainHand(hat);

        return true;
    }
}
