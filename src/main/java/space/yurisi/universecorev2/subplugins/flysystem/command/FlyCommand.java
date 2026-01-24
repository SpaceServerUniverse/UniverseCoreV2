package space.yurisi.universecorev2.subplugins.flysystem.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        // 実行者がプレイヤーか確認　サーバーなどプレイヤー以外ならエラーを出す
        if (!(sender instanceof Player player)) {
            sender.sendMessage("このコマンドはゲーム内から実行してください。");
            return true;
        }

        // 権限を確認 (fly という権限を持っているか)
        // OPは最初から持っているが、Luckparmsで他のプレイヤーにも付与も可能
        if (!player.hasPermission("fly")) {
            player.sendMessage("§cこのコマンドを実行する権限がありません。");
            return true;
        }

        // /fly のみだった場合 (onやoffなどの文字が入っていない場合)
        if (args.length == 0) {
            player.sendMessage("§c使い方が正しくありません: /fly <on|off>");
            return true;
        }

        // flymodeという変数を作ってそのコマンドを全て小文字にして返す
        String flymode = args[0].toLowerCase();

        if (flymode.equals("on")) {
            player.setAllowFlight(true);
            player.sendMessage("§a飛行モードを有効にしました。");
        } else if (flymode.equals("off")) {
            player.setAllowFlight(false);
            player.setFlying(false); // 飛行中にoffにした場合、その場で落下させる
            player.sendMessage("§a飛行モードを無効にしました。");
        } else {
            player.sendMessage("§cコマンドは on または off を指定してください。");
        }

        return true;
    }
}
