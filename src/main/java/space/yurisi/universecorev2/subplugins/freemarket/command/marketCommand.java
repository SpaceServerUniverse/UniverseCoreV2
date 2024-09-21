package space.yurisi.universecorev2.subplugins.freemarket.command;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.exception.MarketItemNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.freemarket.data.JsonConverter;
import space.yurisi.universecorev2.subplugins.freemarket.menu.MarketMenu;
import space.yurisi.universecorev2.subplugins.freemarket.menu.PurchasedItemMenu;
import space.yurisi.universecorev2.subplugins.freemarket.menu.RemoveMenu;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class marketCommand implements CommandExecutor, TabCompleter {

    public static final String FreeMarketMessage = "[フリーマーケット]";

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return true;

        if(strings.length < 1){
            MarketMenu menu = new MarketMenu();
            menu.sendMenu(player);
        }else{
            if(strings[0].equals("sell")){
                if(strings.length < 3){
                    try{
                        long price = Long.parseLong(strings[1]);
                        if(price < 0){
                            Message.sendErrorMessage(player, FreeMarketMessage, "値段は0未満にならないようにしてください");
                            return true;
                        }
                        ItemStack item = player.getInventory().getItemInMainHand();
                        if(item.getType() == Material.AIR){
                            Message.sendErrorMessage(player, FreeMarketMessage, "出品するものを手に持ってください");
                            return true;
                        }
                        UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().createItemData(
                                player.getUniqueId().toString(),
                                item.getType().toString(),
                                item.getItemMeta().hasDisplayName() ? PlainTextComponentSerializer.plainText().serialize(item.getItemMeta().displayName()):null,
                                item.serializeAsBytes(),
                                JsonConverter.ItemStackSerializer(item),
                                JsonConverter.ItemMetaSerializer(item),
                                price
                        );
                        player.getInventory().remove(item);
                        Message.sendSuccessMessage(player, FreeMarketMessage, "出品しました");
                    } catch (NumberFormatException e) {
                        Message.sendErrorMessage(player, FreeMarketMessage, "値段は整数を入力してください");
                        return true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        Message.sendErrorMessage(player, FreeMarketMessage, "値段を入力してください");
                        return true;
                    }
                }
            } else if(strings[0].equals("buy")){
                if(strings.length == 2){
                    Long id = Long.parseLong(strings[1]);
                    try{
                        UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().buyItem(id, player);
                        Message.sendSuccessMessage(player, FreeMarketMessage, "アイテムを購入しました!");
                    } catch (MarketItemNotFoundException e){
                        Message.sendErrorMessage(player, FreeMarketMessage, "アイテムが存在しません");
                        return true;
                    } catch (UserNotFoundException | MoneyNotFoundException | ParameterException | CanNotAddMoneyException e) {
                        Message.sendErrorMessage(player, FreeMarketMessage, e.getMessage());
                        return true;
                    } catch (CanNotReduceMoneyException e){
                        Message.sendErrorMessage(player, FreeMarketMessage, "お金がたりません");
                        return true;
                    }
                }else{
                    Message.sendErrorMessage(player, FreeMarketMessage, "idを指定してください");
                    return true;
                }
            } else if(strings[0].equals("bought")){
                PurchasedItemMenu menu = new PurchasedItemMenu();
                menu.sendMenu(player);
            } else if(strings[0].equals("remove")){
                RemoveMenu menu = new RemoveMenu();
                menu.sendMenu(player);
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if(strings.length == 1) {
            completions.add("sell");
            completions.add("remove");
            completions.add("bought");
        }
        return completions;
    }
}
