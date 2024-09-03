package space.yurisi.universecorev2.subplugins.universeeconomy.file;

import org.bukkit.configuration.file.FileConfiguration;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomy;

public class Config {

    private String unit = "$";

    private Long max = 1000000000L;
    public String getUnit() {
        return unit;
    }

    public Long getMax() {
        return max;
    }
}
