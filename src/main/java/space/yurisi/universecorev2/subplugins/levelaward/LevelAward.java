package space.yurisi.universecorev2.subplugins.levelaward;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.levelaward.command.LevelAwardOpenMenu;
import space.yurisi.universecorev2.subplugins.levelaward.event.*;
import space.yurisi.universecorev2.subplugins.levelaward.utils.Config;

public class LevelAward implements SubPlugin {

    private Config config;
    private static LevelAward instance;

    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new UpEvent(), core);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), core);
        core.getCommand("levelaward").setExecutor(new LevelAwardOpenMenu());
        instance = this;
        this.config = new Config(core);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LevelAward getInstance() {
        return instance;
    }

    public Config getConfig() {
        return config;
    }

    @Override
    public String getName() {
        return "levelaward";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}