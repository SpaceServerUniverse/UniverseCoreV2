package space.yurisi.universecorev2.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.CustomNameRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.CustomNameNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.menu_item.main_menu.LaunchNavigationItem;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.TimeHelper;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.CommandItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class MainMenu implements BaseMenu {

    public void sendMenu(Player player) {
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
        Item inMenuBorder = new SimpleItem(new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE));

        Item executeMywarp = new CommandItem(new ItemBuilder(Material.COMPASS)
                .setDisplayName("マイワープ")
                .setLegacyLore(List.of("§6保存した場所にテレポートします")),
                "/mywarp");
        Item executeMarket = new CommandItem(new ItemBuilder(Material.EMERALD)
                .setDisplayName("マーケット")
                .setLegacyLore(List.of("§6プレイヤー間のアイテム取引ができます")),
                "/market");
        Item executeTpp = new CommandItem(new ItemBuilder(Material.ENDER_PEARL)
                .setDisplayName("プレイヤー間テレポート")
                .setLegacyLore(List.of("§6他のプレイヤーにテレポートできます")),
                "/tpp");
        Item executeGacha = new CommandItem(new ItemBuilder(Material.NETHER_STAR)
                .setDisplayName("ガチャ")
                .setLegacyLore(List.of("§6ガチャを引くことができます")),
                "/gacha");
        Item executeTrash = new CommandItem(new ItemBuilder(Material.LAVA_BUCKET)
                .setDisplayName("ゴミ箱")
                .setLegacyLore(List.of("§6ゴミ箱を開きます。取り扱い注意!")),
                "/trash");
        Item executeTag = new CommandItem(new ItemBuilder(Material.NAME_TAG)
                .setDisplayName("称号設定")
                .setLegacyLore(List.of("§6称号を設定します")),
                "/tag");
        Item executeHead = new CommandItem(new ItemBuilder(Material.PLAYER_HEAD)
                .setDisplayName("プレイヤーヘッド")
                .setLegacyLore(List.of("§6他のプレイヤーのヘッドを購入します")),
                "/head");
        Item executeAmmo = new CommandItem(new ItemBuilder(Material.IRON_NUGGET)
                .setDisplayName("弾薬")
                .setLegacyLore(List.of("§6弾薬を購入・クラフトします")),
                "/ammo");

        Gui gui = Gui.normal()
                .setStructure(
                        "# # # i # = # # #",
                        "# + a m t o y + #",
                        "# + + n s h + + #",
                        "# # # # l # # # #"
                )
                .addIngredient('#', border)
                .addIngredient('+', inMenuBorder)
                .addIngredient('=', createServerInfoItem())
                .addIngredient('i', createPlayerInfoHead(player))
                .addIngredient('a', executeGacha)
                .addIngredient('m', executeMywarp)
                .addIngredient('t', executeTpp)
                .addIngredient('y', executeMarket)
                .addIngredient('n', executeTag)
                .addIngredient('s', executeTrash)
                .addIngredient('h', executeHead)
                .addIngredient('o', executeAmmo)
                .addIngredient('l', new LaunchNavigationItem())
                .build();

        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("メインメニュー")
                .build();

        window.open();
    }

    private Item createPlayerInfoHead(Player player) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        playerHead.setItemMeta(skullMeta);

        DatabaseManager dbManager = UniverseCoreV2API.getInstance().getDatabaseManager();
        UniverseEconomyAPI economyAPI = UniverseEconomyAPI.getInstance();
        UserRepository userRepo = dbManager.getUserRepository();
        CustomNameRepository customNameRepo = dbManager.getCustomNameRepository();

        Long money = 0L;
        String customName = "未設定";

        try {
            User user = userRepo.getUserFromUUID(player.getUniqueId());

            money = economyAPI.getMoney(player);
            if(customNameRepo.existsCustomNameFromUserId(user.getId())) {
                customName = customNameRepo.getCustomNameFromUserId(user.getId()).getDisplay_custom_tag();
            }
        } catch (UserNotFoundException e) {
            money = 0L;
            customName = "§c(取得できませんでした)";
            Message.sendErrorMessage(player,"[管理AI]", "ユーザーデータが見つかりませんでした。");
        } catch (MoneyNotFoundException e) {
            money = 0L;
            Message.sendErrorMessage(player,"[管理AI]", "所持金データが見つかりませんでした。");
        } catch (CustomNameNotFoundException e) {
            customName = "§c(取得できませんでした)";
            Message.sendErrorMessage(player,"[管理AI]", "称号データが見つかりませんでした。");
        }

        String greetingWithName = switch (TimeHelper.checkTime()) {
            case LATE_NIGHT -> "§e遅くまでお疲れ様です、 §r" + player.getName();
            case EARLY_MORNING -> "§f朝早くからお疲れ様です、 §r" + player.getName();
            case MORNING -> "§bおはようございます、 §r" + player.getName();
            case AFTERNOON -> "§aこんにちは、 §r" + player.getName();
            case EVENING -> "§6夜ご飯は何を食べるんですか？ §r" + player.getName();
            case NIGHT -> "§9こんばんは、 §r" + player.getName();
        };

        List<String> playerinfo = List.of(
                "§a名前: §r" + player.getName(),
                "§a称号:§r" + customName,
                "§a所持金: §r" + money + economyAPI.getUnit(),
                "§a現在地: §r" + player.getWorld().getName(),
                "§a座標: §r" + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ(),
                "§aPing値: §r" + player.getPing() + "ms"
        );

        return new SimpleItem(new ItemBuilder(playerHead)
                .setDisplayName(greetingWithName)
                .setLegacyLore(playerinfo));
    }

    private Item createServerInfoItem() {
        Server server = Bukkit.getServer();
        List<String> serverinfo = List.of(
                "§aオンライン人数: §r" + server.getOnlinePlayers().size() + "/" + server.getMaxPlayers(),
                "§aUniverseCoreV2のバージョン: §r" + server.getPluginManager().getPlugin("UniverseCoreV2").getDescription().getVersion(),
                "§aTPS値: §r" + String.format("%.1f, %.1f, %.1f", server.getTPS()[0], server.getTPS()[1], server.getTPS()[2]),
                "§aバージョン: §r" + server.getBukkitVersion() + " - " + server.getVersion()
        );

        return new SimpleItem(new ItemBuilder(Material.SOUL_LANTERN)
                .setDisplayName("§bサーバー情報")
                .setLegacyLore(serverinfo));
    }
}
