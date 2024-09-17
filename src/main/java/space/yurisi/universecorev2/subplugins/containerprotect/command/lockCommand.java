package space.yurisi.universecorev2.subplugins.containerprotect.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.containerprotect.manager.LockManager;

public class lockCommand implements CommandExecutor {

    private LockManager lockManager;

    public lockCommand(LockManager lockManager) {
        this.lockManager = lockManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //課題2 配列にUUID入力 2gyou
        if (!(commandSender instanceof Player player)) {
            return false;
        }

        //もしlockManagerの配列に入ってなかったらlockManagerの配列にいれる
        //たっちしてください！みたいなメッセージを送る

        if(!this.lockManager.isSetState(player)) {
            this.lockManager.setState(player);
            player.sendMessage("ブロックをタッチしてください！");
        }


        return true;
    }
}
