package space.yurisi.universecorev2.subplugins.repaircream.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.material_type.Armor;
import space.yurisi.universecorev2.utils.material_type.Weapon;

public class repairCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!(Armor.isArmor(item.getType())) && !(Weapon.isWeapon(item.getType()))) {
            Message.sendErrorMessage(player, "[修復AI]", "そのアイテムには対応していません。");
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (!(meta instanceof Damageable damageable_meta)) {
            Message.sendErrorMessage(player, "[修復AI]", "そのアイテムには対応していません。");
            return false;
        }

        if (!damageable_meta.hasDamage()) {
            Message.sendErrorMessage(player, "[修復AI]", "アイテムの耐久値は既に最大です。");
            return false;
        }

        Boolean ticket = UniverseItem.removeItem(player, "repair_cream");

        if (!ticket) {
            Message.sendErrorMessage(player, "[修復AI]", "修復クリームが足りません。");
            return false;
        }

        damageable_meta.setDamage(0);
        item.setItemMeta(meta);
        return true;
    }
}
