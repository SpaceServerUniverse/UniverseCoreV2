package space.yurisi.universecorev2.exception;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.item.cooking.CookingItem;

public class NotCookingItemException extends RuntimeException {
    public NotCookingItemException(CookingItem item, int row) {
        super();
    }
}
