package space.yurisi.universecorev2.subplugins.mywarp.command.subcommand;

import org.bukkit.command.CommandSender;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;

public interface MywarpSubCommand {

    boolean execute(UniverseCoreAPIConnector connector, CommandSender sender, String[] args);
}
