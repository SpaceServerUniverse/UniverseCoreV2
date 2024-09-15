package space.yurisi.universecorev2.subplugins.tppsystem;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.tppsystem.command.TPPCommand;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.file.Config;

public class TPPSystem implements SubPlugin {

    private UniverseCoreAPIConnector connector;

    private Config config;


    public void onEnable(UniverseCoreV2 core) {
        // Plugin startup logic
        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();
        this.connector = new UniverseCoreAPIConnector(manager);
        this.config = new Config(core);
        core.getCommand("tpp").setExecutor(new TPPCommand(connector));
    }

    public Config getPluginConfig(){
        return this.config;
    }

    public UniverseCoreAPIConnector getConnector(){
        return this.connector;
    }

    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "TPPSystem";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

}
