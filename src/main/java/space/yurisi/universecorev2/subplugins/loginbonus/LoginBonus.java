package space.yurisi.universecorev2.subplugins.loginbonus;

import org.bukkit.Bukkit;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.loginbonus.command.LoginBonusCommand;
import space.yurisi.universecorev2.subplugins.loginbonus.listener.JoinEventListener;

public class LoginBonus implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        Bukkit.getPluginManager().registerEvents(new JoinEventListener(), core);
        core.getCommand("loginbonus").setExecutor(new LoginBonusCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "LoginBonus";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
