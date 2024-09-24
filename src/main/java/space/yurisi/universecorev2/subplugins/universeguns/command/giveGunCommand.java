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
            sender.sendMessage("Please specify the gun name.");
            return false;
        }

        switch (args[0]){
            case "R4C":
                // Give R4C
                GunItem item = ItemRegister.getItem("r4c");
                ItemStack itemStack = item.getItem();
                ((Player) sender).getInventory().addItem(itemStack);
                break;
            case "SMG11":
                // Give M4
                break;
            case "AK48":
                // Give AK47
                break;
            default:
                sender.sendMessage("Please specify the gun name.");
                return false;
        }
        return true;
    }
}
