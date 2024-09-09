package space.yurisi.universecorev2.subplugins.mywarp;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.mywarp.command.MywarpCommand;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.file.Config;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;

public final class Mywarp implements SubPlugin {

    private UniverseCoreAPIConnector connector;

    private Config config;

    public void onEnable(UniverseCoreV2 core) {
        // Plugin startup logic
        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();
        this.config = new Config(core);
        this.connector = new UniverseCoreAPIConnector(manager, this.config);
        core.getCommand("mywarp").setExecutor(new MywarpCommand(connector));
        core.getCommand("mw").setExecutor(new MywarpCommand(connector));
    }

    public UniverseCoreAPIConnector getConnector(){
        return this.connector;
    }

    public Config getPluginConfig(){
        return this.config;
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "Mywarp";
    }

    @Override
    public String getVersion() {
        return "1.2.3";
    }
}
