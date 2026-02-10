package space.yurisi.universecorev2.subplugins.flysystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.api.LuckPermsWrapper;

import java.util.Locale;

public class FlyCommand implements CommandExecutor {

    private final FlySystemMessageFormatter formatter;

    public FlyCommand() {
        this.formatter = new FlySystemMessageFormatter();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("このコマンドはゲーム内から実行してください。");
            return false;
        }

        // TODO: とりあえず管理者と開発者のみ使用可能にしているが，拡張性がなさすぎるのでいつか直す
        if (!LuckPermsWrapper.isUserInAdminOrDevGroup(player)) {
            player.sendMessage(formatter.formatError("このコマンドを実行する権限がありません。"));
            return false;
        }

        boolean newFlyState = !player.getAllowFlight();
        player.setAllowFlight(newFlyState);
        if (!newFlyState) {
            player.setFlying(false);
        }

        player.sendMessage(formatter.format(newFlyState ? "飛行モードを有効にしました。" : "飛行モードを無効にしました。"));
        return true;
    }
}
