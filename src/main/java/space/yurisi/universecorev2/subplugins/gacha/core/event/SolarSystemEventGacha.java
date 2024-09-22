package space.yurisi.universecorev2.subplugins.gacha.core.event;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.solar_system.*;

import java.util.Objects;

public class SolarSystemEventGacha extends EventGacha {

    public SolarSystemEventGacha(Player player) {
        super(player);
    }

    @Override
    protected void initializeSuperRareList() {
        try {
            super_rare.add(((LevellingCustomItem) Objects.requireNonNull(UniverseItem.getItem(SolarSystemSword.id))).getItem(1));
            super_rare.add(((LevellingCustomItem) Objects.requireNonNull(UniverseItem.getItem(SolarSystemShovel.id))).getItem(1));
            super_rare.add(((LevellingCustomItem) Objects.requireNonNull(UniverseItem.getItem(SolarSystemPickaxe.id))).getItem(1));
            super_rare.add(((LevellingCustomItem) Objects.requireNonNull(UniverseItem.getItem(SolarSystemAxe.id))).getItem(1));
        } catch (CustomItemLevelNotFoundException ignored) {
            super_rare.add(getErrorItem());
        }
    }

    @Override
    protected void initializeUltraRareList() {
        ultra_rare.add(UniverseItem.getItem(SolarSystemHead.id).getItem());
        ultra_rare.add(UniverseItem.getItem(SolarSystemChestplate.id).getItem());
        ultra_rare.add(UniverseItem.getItem(SolarSystemLeggings.id).getItem());
        ultra_rare.add(UniverseItem.getItem(SolarSystemBoots.id).getItem());
    }
}
