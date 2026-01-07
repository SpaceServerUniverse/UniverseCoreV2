package space.yurisi.universecorev2.subplugins.freemarket.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
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
import space.yurisi.universecorev2.exception.MarketItemNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.OwnerBuyMarketItemException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.freemarket.data.Config;
import space.yurisi.universecorev2.utils.JsonConverter;
import space.yurisi.universecorev2.subplugins.freemarket.menu.MarketMenu;
import space.yurisi.universecorev2.subplugins.freemarket.menu.PurchasedItemMenu;
import space.yurisi.universecorev2.subplugins.freemarket.menu.RemoveMenu;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;

public class marketCommand implements CommandExecutor, TabCompleter {

    public static final String FreeMarketMessage = "[フリーマーケット]";
    private final Config config;

    public marketCommand(Config config){
        this.config = config;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) return true;

        if (args.length == 0) {
            MarketMenu menu = new MarketMenu();
            menu.sendMenu(player);
            return false;
        }

        switch (args[0]) {
            case "sell":
                try {
                    long price = Long.parseLong(args[1]);

                    if (!checkCanSell(player, price)) return false;

                    ItemStack item = player.getInventory().getItemInMainHand();
                    UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().createItemData(
                            player.getUniqueId().toString(),
                            item.getType().toString(),
                            item.getItemMeta().hasDisplayName() ? PlainTextComponentSerializer.plainText().serialize(item.getItemMeta().displayName()) : null,
                            item.serializeAsBytes(),
                            JsonConverter.ItemStackSerializer(item),
                            JsonConverter.ItemMetaSerializer(item),
                            price
                    );

                    player.getInventory().getItemInMainHand().setAmount(0);
                    Message.sendSuccessMessage(player, FreeMarketMessage, "出品しました");
                    return true;
                } catch (NumberFormatException e) {
                    Message.sendErrorMessage(player, FreeMarketMessage, "値段は整数を入力してください");
                    return false;
                } catch (ArrayIndexOutOfBoundsException e) {
                    Message.sendErrorMessage(player, FreeMarketMessage, "値段を入力してください");
                    return false;
                }
            case "buy":
                if (args.length < 2) {
                    Message.sendErrorMessage(player, FreeMarketMessage, "idを指定してください");
                    return false;
                }

                Long id = Long.parseLong(args[1]);

                Market item;

                try {
                    UniverseCoreV2API.getInstance().getDatabaseManager().getMarketRepository().buyItem(id, player);
                    Message.sendSuccessMessage(player, FreeMarketMessage, "アイテムを購入しました! 受取を行って下さい！");
                    Component component = Component.text("§a[フリーマーケット] §l§n[ここをクリックで受取]")
                            .clickEvent(ClickEvent.runCommand("/market bought"))
                            .hoverEvent(HoverEvent.showText(Component.text("クリックで受取")));
                    player.sendMessage(component);
                } catch (MarketItemNotFoundException e) {
                    Message.sendErrorMessage(player, FreeMarketMessage, "アイテムが存在しません");
                    return false;
                } catch (UserNotFoundException | MoneyNotFoundException | ParameterException |
                         CanNotAddMoneyException e) {
                    Message.sendErrorMessage(player, FreeMarketMessage, e.getMessage());
                    return false;
                } catch (CanNotReduceMoneyException e) {
                    Message.sendErrorMessage(player, FreeMarketMessage, "お金がたりません");
                    return false;
                } catch (OwnerBuyMarketItemException e) {
                    Message.sendErrorMessage(player, FreeMarketMessage, "自分の出品したアイテムは購入できません");
                    return false;
                }
                break;
            case "bought":
                PurchasedItemMenu menu = new PurchasedItemMenu();
                menu.sendMenu(player);
                break;
            case "remove":
                RemoveMenu removeMenu = new RemoveMenu();
                removeMenu.sendMenu(player);
                break;
            default:
                String[] helpMessage = """
            §6-- Market Help --
               §7/market sell <金額> : 手に持っているアイテムを指定した金額で出品します
               §7/market bought : 購入済みのアイテムを受け取ります
               §7/market remove : 出品中のアイテムを削除します
            """.split("\n");
                player.sendMessage(helpMessage);
                break;
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (strings.length == 1) {
            completions.add("sell");
            completions.add("remove");
            completions.add("bought");
        }
        return completions;
    }

    private boolean checkCanSell(Player player, long price) {
        if (price < 0) {
            Message.sendErrorMessage(player, FreeMarketMessage, "値段は0未満にならないようにしてください");
            return false;
        }

        if (price > this.config.getLimit()){
            Message.sendErrorMessage(player, FreeMarketMessage, "値段は [" + this.config.getLimit().toString() + "] 以上にならないようにしてください");
            return false;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            Message.sendErrorMessage(player, FreeMarketMessage, "出品するものを手に持ってください");
            return false;
        }
        return true;
    }
}
