package space.yurisi.universecorev2.subplugins.gacha.constrants;

import space.yurisi.universecorev2.item.solar_system.*;

import java.util.List;

public enum GachaType {
    SOLAR_SYSTEM("SolarSystemガチャ", "solar_system",
            List.of(
                SolarSystemSword.id,
                SolarSystemShovel.id,
                SolarSystemPickaxe.id,
                SolarSystemAxe.id
            ),
            List.of(
                SolarSystemHead.id,
                SolarSystemChestplate.id,
                SolarSystemLeggings.id,
                SolarSystemBoots.id
            ));

    private final String typeName;
    private final String id;
    private final List<String> SRItems;
    private final List<String> URItems;

    GachaType(String typeName, String id, List<String> SRItems, List<String> URItems) {
        this.typeName = typeName;
        this.id = id;
        this.SRItems = SRItems;
        this.URItems = URItems;
    }
    public String getTypeName() {
        return typeName;
    }
    public String getId() {
        return id;
    }
    public List<String> getSRItems() {
        return SRItems;
    }
    public List<String> getURItems() {
        return URItems;
    }
}
