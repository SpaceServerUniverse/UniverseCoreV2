package space.yurisi.universecorev2.subplugins.freemarket.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Market;

public class marketCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        Market market;
        market = UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().getItems().getFirst();
        ItemStack itemStack = ItemStack.deserializeBytes(market.getSerializedItem());
        ((Player) commandSender).getInventory().addItem(itemStack);

        return true;
    }
}
