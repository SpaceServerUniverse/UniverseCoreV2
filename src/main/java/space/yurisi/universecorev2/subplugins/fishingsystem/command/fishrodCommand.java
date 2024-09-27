package space.yurisi.universecorev2.subplugins.fishingsystem.command;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class fishrodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        ItemStack rod = ItemStack.of(Material.FISHING_ROD);
        ItemMeta meta = rod.getItemMeta();
        NamespacedKey key = new NamespacedKey("fishing_hook", "custom");
        PersistentDataContainer data = meta.getPersistentDataContainer();
        int number = Integer.parseInt(args[0]);

        if (!(sender instanceof Player player)) return false;
        if (args.length == 0) return false;

        if (number > 0 && number < 5) {
            data.set(key, PersistentDataType.INTEGER, number);
            meta.setDisplayName("釣り竿 レア度:" + number);
            rod.setItemMeta(meta);
            player.getInventory().addItem(rod);
            return true;
        }
        return true;
    }
}
