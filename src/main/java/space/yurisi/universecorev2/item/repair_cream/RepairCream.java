package space.yurisi.universecorev2.item.repair_cream;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.CustomItem;

public class RepairCream extends CustomItem {

    public static final String id = "repair_cream";

    public RepairCream() {
        super(
                id,
                "§d§l修復クリーム",
                ItemStack.of(Material.MAGMA_CREAM)
        );
    }

    @Override
    protected void registerItemFunction() {
        default_setting = (item) -> item;
    }
}
