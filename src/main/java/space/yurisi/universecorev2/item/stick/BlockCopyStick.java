package space.yurisi.universecorev2.item.stick;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.CustomItem;

public class BlockCopyStick extends CustomItem {

    public static final String id = "block_copy_stick";

    public BlockCopyStick() {
        super(
                id,
                "§dくらぅんの§a最強ツール",
                ItemStack.of(Material.STICK));
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> item;
    }
}