package space.yurisi.universecorev2.subplugins.loginbonus.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.exception.CustomItemLevelNotFoundException;
import space.yurisi.universecorev2.item.CustomItem;
import space.yurisi.universecorev2.item.LevellingCustomItem;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.fishingrod.FishingRod;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import space.yurisi.universecorev2.item.ticket.GunTicket;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class LoginBonusType {

    public String getLoginBonusNameByCalendar(Calendar calendar){
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY,
                    Calendar.WEDNESDAY,
                    Calendar.SATURDAY -> "§eガチャチケット5枚";

            case Calendar.MONDAY,
                    Calendar.FRIDAY -> "§e10000$";

            case Calendar.TUESDAY -> "§e特殊釣り竿1つ";

            case Calendar.THURSDAY -> "§e銃チケット1枚";

            default -> "エラー";
        };
    }

    public ItemBuilder getItemBuilderByCalendar(Calendar calendar) {
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY,
                    Calendar.WEDNESDAY,
                    Calendar.SATURDAY -> new ItemBuilder(Material.PAPER)
                    .setDisplayName(calendar.get(Calendar.DATE) + "日")
                    .setLegacyLore(List.of("§eガチャチケット5枚"));

            case Calendar.MONDAY,
                    Calendar.FRIDAY -> new ItemBuilder(Material.GOLD_BLOCK)
                    .setDisplayName(calendar.get(Calendar.DATE) + "日")
                    .setLegacyLore(List.of("§e10000$"));

            case Calendar.TUESDAY -> new ItemBuilder(Material.FISHING_ROD)
                    .setDisplayName(calendar.get(Calendar.DATE) + "日")
                    .setLegacyLore(List.of("§e特殊釣り竿1つ"));

            case Calendar.THURSDAY -> new ItemBuilder(Material.PAPER)
                    .setDisplayName(calendar.get(Calendar.DATE) + "日")
                    .setLegacyLore(List.of("§e銃チケット1枚"));

            default -> new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                    .setDisplayName("エラー");
        };
    }

    public ItemStack getItemStackByCalendar(Calendar calendar) {
        return switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY,
                    Calendar.WEDNESDAY,
                    Calendar.SATURDAY -> {
                ItemStack item = UniverseItem.getItem(GachaTicket.id).getItem().clone();
                item.setAmount(5);
                yield item;
            }

            case Calendar.MONDAY,
                    Calendar.FRIDAY -> {
                ItemStack money = new ItemStack(Material.GOLD_NUGGET);
                ItemMeta meta = money.getItemMeta();
                meta.setDisplayName("§e10000$");
                money.setItemMeta(meta);
                yield money;
            }

            case Calendar.TUESDAY -> {
                try {
                    LevellingCustomItem item = (LevellingCustomItem) UniverseItem.getItem(FishingRod.id);
                    ItemStack ui = item.getItem(1);

                    yield ui != null ? ui.clone()
                            : new ItemStack(Material.BARRIER);
                }catch (CustomItemLevelNotFoundException e){
                   yield new ItemStack(Material.BARRIER);
                }
            }

            case Calendar.THURSDAY -> {
                CustomItem ui = UniverseItem.getItem(GunTicket.id);
                yield ui != null ? ui.getItem().clone()
                        : new ItemStack(Material.BARRIER);
            }

            default -> new ItemStack(Material.BARRIER);
        };
    }
}
