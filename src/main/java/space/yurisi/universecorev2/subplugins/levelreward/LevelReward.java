package space.yurisi.universecorev2.subplugins.levelreward;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.levelreward.command.rewardCommand;
import space.yurisi.universecorev2.subplugins.levelreward.event.UpEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.command.addexpCommand;

import java.util.Objects;

public class LevelReward implements SubPlugin {


    public void onEnable(UniverseCoreV2 core) {
        Objects.requireNonNull(core.getCommand("reward")).setExecutor(new rewardCommand());
        Bukkit.getPluginManager().registerEvents(new UpEvent(), core);
    }


    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "levelreward";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}