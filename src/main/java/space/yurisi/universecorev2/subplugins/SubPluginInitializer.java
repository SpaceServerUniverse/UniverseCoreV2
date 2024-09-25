package space.yurisi.universecorev2.subplugins;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.blockcopystick.BlockCopyStick;
import space.yurisi.universecorev2.subplugins.changemessages.ChangeMessages;
import space.yurisi.universecorev2.subplugins.customname.CustomName;
import space.yurisi.universecorev2.subplugins.gacha.Gacha;
import space.yurisi.universecorev2.subplugins.damagemanager.DamageManager;
import space.yurisi.universecorev2.subplugins.itemhat.ItemHat;
import space.yurisi.universecorev2.subplugins.freemarket.FreeMarket;
import space.yurisi.universecorev2.subplugins.levelaward.LevelAward;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.mywarp.Mywarp;
import space.yurisi.universecorev2.subplugins.playerhead.PlayerHead;
import space.yurisi.universecorev2.subplugins.playerinfoscoreboard.PlayerInfoScoreBoard;
import space.yurisi.universecorev2.subplugins.rankcounter.RankCounter;
import space.yurisi.universecorev2.subplugins.repaircream.RepairCream;
import space.yurisi.universecorev2.subplugins.universeguns.UniverseGuns;
import space.yurisi.universecorev2.subplugins.universeutilcommand.UniverseUtilCommand;
import space.yurisi.universecorev2.subplugins.signcommand.SignCommand;
import space.yurisi.universecorev2.subplugins.sitdown.Sitdown;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscord;
import space.yurisi.universecorev2.subplugins.containerprotect.ContainerProtect;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomy;
import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.xtpsystem.XtpSystem;

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
        this.subPlugins.add(new LevelAward());
        this.subPlugins.add(new UniverseLand());
        this.subPlugins.add(new UniverseDiscord());
        this.subPlugins.add(new Mywarp());
        this.subPlugins.add(new RankCounter());
        this.subPlugins.add(new SignCommand());
        this.subPlugins.add(new XtpSystem());
        this.subPlugins.add(new TPPSystem());
        this.subPlugins.add(new Sitdown());
        this.subPlugins.add(new UniverseUtilCommand());
        this.subPlugins.add(new ItemHat());
        this.subPlugins.add(new CustomName());
        //this.subPlugins.add(new ContainerProtect());
        this.subPlugins.add(new PlayerHead());
        this.subPlugins.add(new FreeMarket());
        this.subPlugins.add(new Gacha());
        this.subPlugins.add(new BlockCopyStick());
        //this.subPlugins.add(new DamageManager());
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
