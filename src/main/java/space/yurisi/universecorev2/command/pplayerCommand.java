package space.yurisi.universecorev2.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.models.UserPosition;
import space.yurisi.universecorev2.exception.PositionNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.exception.UserPositionNotFoundException;

public class pplayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        DatabaseManager manager = UniverseCoreV2API.getInstance().getDatabaseManager();

        try {
            User user = manager.getUserRepository().getUserFromUUID(player.getUniqueId());
            UserPosition userPosition = manager.getUserPositionRepository().getUserPositionFromUser(user);
            String position = manager.getPositionRepository().getNameFromUserPosition(userPosition);
            player.sendMessage(Component.text(player.getName()+"さんの役職は"+position+"です。"));
        } catch (UserPositionNotFoundException e) {
            player.sendMessage(Component.text(player.getName()+"さんの役職はなしです。"));
        } catch (UserNotFoundException e) {
            player.sendMessage(Component.text("エラーが発生しました ユーザーデータが存在しません。 エラーコード:UP0001"));
        } catch (PositionNotFoundException e) {
            player.sendMessage(Component.text("エラーが発生しました 役職データが存在しません。 エラーコード:UP0002"));
        }
        return true;
    }
}
