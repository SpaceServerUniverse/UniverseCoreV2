package space.yurisi.universecorev2.subplugins.positionsystem.command;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.models.UserPosition;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.exception.UserPositionNotFoundException;
import space.yurisi.universecorev2.utils.Message;

public class gmCommand implements CommandExecutor {

    private static final String PREFIX = "[管理AI]";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();
        try {
            User user = manager.getUserRepository().getUserFromUUID(player.getUniqueId());
            UserPosition userPosition = manager.getUserPositionRepository().getUserPositionFromUser(user);
            Long positionId = userPosition.getPosition_id();
            if (positionId == null || positionId < 4) {
                Message.sendErrorMessage(player, PREFIX, "権限がありません");
                return true;
            }
        } catch (UserPositionNotFoundException e) {
            Message.sendErrorMessage(player, PREFIX, "役職が設定されていません");
            return true;
        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, PREFIX, "ユーザーデータが存在しません");
            return true;
        }

        GameMode nextMode = player.getGameMode() == GameMode.SPECTATOR ? GameMode.SURVIVAL : GameMode.SPECTATOR;
        player.setGameMode(nextMode);

        String modeName = nextMode == GameMode.SPECTATOR ? "スペクテイター" : "サバイバル";
        Message.sendSuccessMessage(player, PREFIX, "ゲームモードを" + modeName + "に変更しました");
        return true;
    }
}
