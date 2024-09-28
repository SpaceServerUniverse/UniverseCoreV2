package space.yurisi.universecorev2.subplugins.evolutionitem.event;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.utils.material_type.Armor;
import space.yurisi.universecorev2.utils.material_type.Weapon;

import java.util.Objects;

public class PrepareAnvil implements Listener {
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inventory = event.getInventory();
        ItemStack firstItem = inventory.getItem(0);
        ItemStack secondItem = inventory.getItem(1);

        if(firstItem != null && firstItem.getType() == Material.PAPER){
            return;
        }

        if (firstItem == null && secondItem != null) {
            if (Weapon.isWeapon(secondItem.getType()) || Armor.isArmor(secondItem.getType())) {
                event.setResult(null);
                return;
            }
        }

        if (secondItem == null && firstItem != null) {
            if (Weapon.isWeapon(firstItem.getType()) || Armor.isArmor(firstItem.getType())) {
                event.setResult(null);
                return;
            }
        }


        if (firstItem == null || secondItem == null) {
            event.setResult(null);
            return;
        }

        ItemMeta firstItemMeta = firstItem.getItemMeta();
        ItemMeta secondItemMeta = secondItem.getItemMeta();

        if (firstItemMeta == null || secondItemMeta == null) {
            event.setResult(null);
            return;
        }

        PersistentDataContainer firstItemContainer = firstItemMeta.getPersistentDataContainer();
        PersistentDataContainer secondItemContainer = secondItemMeta.getPersistentDataContainer();

        NamespacedKey itemKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.ITEM_NAME);
        NamespacedKey levelKey = new NamespacedKey(UniverseCoreV2.getInstance(), UniverseItemKeyString.LEVEL);

        boolean firstItemHasLevel = firstItemContainer.has(levelKey, PersistentDataType.INTEGER);
        boolean secondItemHasLevel = secondItemContainer.has(levelKey, PersistentDataType.INTEGER);

        if (!firstItemContainer.has(itemKey, PersistentDataType.STRING) || !firstItemHasLevel) {
            event.setResult(null);
            return;
        }

        if (!secondItemContainer.has(itemKey, PersistentDataType.STRING) || !secondItemHasLevel) {
            event.setResult(null);
            return;
        }

        String firstItemKeyName = firstItemContainer.get(itemKey, PersistentDataType.STRING);
        String secondItemKeyName = secondItemContainer.get(itemKey, PersistentDataType.STRING);

        if (!Objects.equals(firstItemKeyName, secondItemKeyName)) {
            event.setResult(null);
            return;
        }

        CustomItem newItem = UniverseItem.getItem(firstItemKeyName);
        if (!(newItem instanceof LevellingCustomItem levellingCustomItem)) {
            event.setResult(null);
            return;
        }

        int firstItemKeyLevel = firstItemContainer.get(levelKey, PersistentDataType.INTEGER);
        int secondItemKeyLevel = secondItemContainer.get(levelKey, PersistentDataType.INTEGER);

        int nextLevel = Math.max(firstItemKeyLevel, secondItemKeyLevel) + 1;

        nextLevel = Math.min(nextLevel, levellingCustomItem.getMaxLevel());

        try {
            ItemStack newItemStack = levellingCustomItem.getItem(nextLevel);
            event.setResult(newItemStack);
        } catch (CustomItemLevelNotFoundException ignored) {
        }
    }
}
