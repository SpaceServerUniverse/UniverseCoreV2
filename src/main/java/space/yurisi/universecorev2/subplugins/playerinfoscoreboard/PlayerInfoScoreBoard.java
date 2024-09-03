package space.yurisi.universecorev2.subplugins.playerinfoscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.event.player.JoinEvent;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.event.player.QuitEvent;

public final class PlayerInfoScoreBoard implements SubPlugin {

    public void onEnable(UniverseCoreV2 core) {
        TaskManager taskManager = new TaskManager(core);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(taskManager), core);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(taskManager), core);
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "PlayerInfoScoreBoard";
    }

    @Override
    public String getVersion() {
        return "1.1";
    }
}
