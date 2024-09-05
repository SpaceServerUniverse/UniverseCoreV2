package space.yurisi.universecorev2.subplugins.mywarp.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.command.CommandExecutor;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;


public class MywarpBaseCommand {

    protected final String title = "[テレポートAI] ";

    protected UniverseCoreAPIConnector connector;

    protected Component getSuccessMessage(String message){
        return Component.text(title + message).color(TextColor.color(Color.GREEN.asRGB()));
    }

    protected Component getErrorMessage(String message){
        return Component.text(title + message).color(TextColor.color(Color.RED.asRGB()));
    }
}
