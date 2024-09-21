package space.yurisi.universecorev2.subplugins.tppsystem.command.subcommand;

import org.bukkit.command.CommandSender;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;

public interface TPPSubCommand {

    boolean execute(UniverseCoreAPIConnector connector, RequestManager requestManager, CommandSender sender, String[] args);
}
