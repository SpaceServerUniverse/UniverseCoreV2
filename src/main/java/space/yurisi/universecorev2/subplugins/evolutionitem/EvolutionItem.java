package space.yurisi.universecorev2.subplugins.evolutionitem;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.evolutionitem.event.PrepareAnvil;

public class EvolutionItem implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getServer().getPluginManager().registerEvents(new PrepareAnvil(), core);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "DamageManager";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
