package space.yurisi.universecorev2.subplugins.universeguns.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeguns.item.GunItem;
import space.yurisi.universecorev2.subplugins.universeguns.item.ItemRegister;

public class giveGunCommand implements CommandExecutor {

    private final UniverseCoreV2 main;

    public giveGunCommand(UniverseCoreV2 main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }

        if(args.length == 0){
            sender.sendMessage("有効な銃の名前を指定してください。");
            return false;
        }

        String gunId = "";
        switch (args[0]){
            case "R4C", "r4c":
                gunId = "r4c";
                break;
            case "SMG11":
                // Give M4
                break;
            case "AK48":
                // Give AK47
                break;
            case "RPG","rpg":
                // Give RPG7
                gunId = "rpg";
                break;
            default:
                sender.sendMessage("有効な銃の名前を指定してください。");
                return false;
        }
        GunItem item = ItemRegister.getItem(gunId);
        ItemStack itemStack = item.getItem();
        ((Player) sender).getInventory().addItem(itemStack);
        return true;
    }
}
