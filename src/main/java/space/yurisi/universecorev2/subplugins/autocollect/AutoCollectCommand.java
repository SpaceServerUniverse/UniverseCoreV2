package space.yurisi.universecorev2.subplugins.autocollect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AutoCollectCommand implements CommandExecutor {

    private final AutoCollectManager manager;
    private final AutoCollectMessageFormatter messageFormatter;

    public AutoCollectCommand(AutoCollectManager manager, AutoCollectMessageFormatter messageFormatter) {
        this.manager = manager;
        this.messageFormatter = messageFormatter;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(messageFormatter.formatError("このコマンドはプレイヤーのみ使用できます"));
            return false;
        }

        AutoCollectState newState = manager.toggleAutoCollect(player);
        sender.sendMessage(messageFormatter.formatToggleMessage(player, newState));
        
        return true;
    }

}
