package space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.item.MywarpItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.menu_item.TopItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.item.NoItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.del_confirm_menu.item.YesItem;
import space.yurisi.universecorev2.subplugins.mywarp.menu.list_menu.item.WarpMenuItem;
import space.yurisi.universecorev2.subplugins.mywarp.utils.MessageHelper;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class DelConfirmMywarpInventoryMenu implements BaseMenu {

    private UniverseCoreAPIConnector connector;

    private Mywarp mywarp;

    public DelConfirmMywarpInventoryMenu(UniverseCoreAPIConnector connector, Mywarp mywarp){
        this.connector = connector;
        this.mywarp = mywarp;
    }

    @Override
    public void sendMenu(Player player) {
        try{
            List<Item> items = this.connector.getMywarpList(player).stream()
                    .map(mywarp -> new WarpMenuItem(connector, mywarp))
                    .collect(Collectors.toList());

            Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
            Gui gui = PagedGui.items()
                    .setStructure(
                            "# # # # i # # # #",
                            "# # # y # n # # #",
                            "# # # # # # # # #",
                            "# # # # b # # # #")
                    .addIngredient('#', border)
                    .addIngredient('i', new MywarpItem(this.mywarp))
                    .addIngredient('b', new TopItem(this.connector))
                    .addIngredient('y', new YesItem(this.connector, this.mywarp))
                    .addIngredient('n', new NoItem(this.connector, this.mywarp))
                    .setContent(items)
                    .build();

            Window window = Window.single()
                    .setViewer(player)
                    .setGui(gui)
                    .setTitle("本当に削除しますか？")
                    .build();

            window.open();
        } catch (UserNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("ユーザーデータが存在しないようです。管理者に報告してください。"));
        } catch (MywarpNotFoundException e) {
            player.sendMessage(MessageHelper.getErrorMessage("ワープポイントが見つかりませんでした。"));
        }
    }
}
