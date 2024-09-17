package space.yurisi.universecorev2.subplugins.containerprotect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.LockManager;
import space.yurisi.universecorev2.utils.Message;

public class unlockCommand implements CommandExecutor {

    private LockManager lockManager;

    public unlockCommand(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        if(lockManager.hasFlag(player, LockManager.UNLOCK)) {
            lockManager.setFlag(player, LockManager.UNLOCK);
            Message.sendNormalMessage(player, "[金庫AI]", "保護を解除したいコンテナをクリックしてください");
        }else{
            Message.sendSuccessMessage(player, "[金庫AI]", "キャンセルしました");
        }

        return true;
    }
}
