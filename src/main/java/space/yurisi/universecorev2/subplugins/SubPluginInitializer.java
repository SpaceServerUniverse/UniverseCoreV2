package space.yurisi.universecorev2.subplugins;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.changemessages.ChangeMessages;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.PlayerInfoScoreBoard;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomy;

import java.util.ArrayList;
import java.util.List;

public class SubPluginInitializer {

    private List<SubPlugin> subPlugins = new ArrayList<>();

    private UniverseCoreV2 core;

    public SubPluginInitializer(UniverseCoreV2 core){
        this.core = core;
        registerPlugin();
    }

    private void registerPlugin(){
        this.subPlugins.add(new PlayerInfoScoreBoard());
        this.subPlugins.add(new LevelSystem());
        this.subPlugins.add(new UniverseEconomy());
        this.subPlugins.add(new ChangeMessages());
    }

    public void onEnable(){
        for (SubPlugin subplugin: subPlugins){
            subplugin.onEnable(this.core);
            Bukkit.getLogger().info(subplugin.getName()+" v"+ subplugin.getVersion()+" Loaded");
        }
    }

    public void onDisable(){
        for(SubPlugin subPlugin: subPlugins){
            subPlugin.onDisable();
        }
    }


}
