package space.yurisi.universecorev2.subplugins.universeslot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.utils.Message;

public class SlotCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        UniverseSlot main = UniverseSlot.getInstance();

        if(main.getPlayerStatusManager().hasFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE)){
            main.getPlayerStatusManager().removeFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE);
            Message.sendSuccessMessage(player, "[スロットAI]", "スロット編集モードに入りました。登録/解除したいスロットを右クリックしてください。");
        } else {
            main.getPlayerStatusManager().addFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE);
            Message.sendNormalMessage(player, "[スロットAI]", "スロット編集モードを解除しました。");
        }
        return true;

    }
}
