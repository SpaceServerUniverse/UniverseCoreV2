package space.yurisi.universecorev2.subplugins.cooking;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.cooking.command.CookingCommand;
import space.yurisi.universecorev2.subplugins.cooking.event.CookingEventListener;

public class Cooking implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        new CookingAPI();
        core.getServer().getPluginManager().registerEvents(new CookingEventListener(), core);
        core.getCommand("cooking").setExecutor(new CookingCommand());
        core.getCommand("cooking").setTabCompleter(new CookingCommand());
    }

    @Override
    public void onDisable() {
        CookingAPI.getInstance().save();
    }

    @Override
    public String getName() {
        return "Cooking";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
