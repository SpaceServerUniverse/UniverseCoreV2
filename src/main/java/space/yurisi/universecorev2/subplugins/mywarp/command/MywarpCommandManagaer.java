package space.yurisi.universecorev2.subplugins.mywarp.command;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.mywarp.Mywarp;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;

public class MywarpCommandManagaer {

    public MywarpCommandManagaer(UniverseCoreV2 core, UniverseCoreAPIConnector connector) {
        init(core, connector);
    }

    private void init(UniverseCoreV2 core, UniverseCoreAPIConnector connector) {
        core.getCommand("mywarp").setExecutor(new MywarpHelpCommand(connector));
        core.getCommand("mwadd").setExecutor(new MywarpCreateCommand(connector));
        core.getCommand("mwdel").setExecutor(new MywarpDeleteCommand(connector));
        core.getCommand("mwlist").setExecutor(new MywarpListCommand(connector));
        core.getCommand("mwtp").setExecutor(new MywarpTeleportCommand(connector));
        core.getCommand("mwvisit").setExecutor(new MywarpVisitCommand(connector));
        core.getCommand("mwvisitlist").setExecutor(new MywarpVisitListCommand(connector));
    }
}

