package space.yurisi.universecorev2.subplugins.fishingsystem;

import org.bukkit.inventory.ItemStack;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishFeed;
import space.yurisi.universecorev2.subplugins.fishingsystem.constants.FishingRarity;

public record Fish(String name, double min, double max, FishingRarity rarity, FishFeed feed, ItemStack baseItem) {
}
