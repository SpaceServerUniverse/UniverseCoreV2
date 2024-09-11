package space.yurisi.universecorev2.subplugins.mywarp.menu.visit_menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.mywarp.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.mywarp.menu.menu_item.*;
import space.yurisi.universecorev2.subplugins.mywarp.menu.visit_menu.item.VisitMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.stream.Collectors;

public class VisitMywarpInventoryMenu implements BaseMenu {

    private UniverseCoreAPIConnector connector;

    private String target;

    public VisitMywarpInventoryMenu(UniverseCoreAPIConnector connector, String target){
            this.connector = connector;
            this.target =  target;
    }

    public void sendMenu(Player player){

            try{
                List<Item> items = this.connector.getPublicMywarpListFromName(this.target).stream()
                        .map(mywarp -> new VisitMenuItem(connector, mywarp))
                        .collect(Collectors.toList());
                Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
                Gui gui = PagedGui.items()
                        .setStructure(
                                "# # # # # # # # #",
                                "# x x x x x x x #",
                                "# x x x x x x x #",
                                "# # # < b > # # #")
                        .addIngredient('#', border)
                        .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                        .addIngredient('b', new TopItem(this.connector))
                        .addIngredient('<', new BackItem())
                        .addIngredient('>', new ForwardItem())
                        .setContent(items)
                        .build();

                xyz.xenondevs.invui.window.Window window = Window.single()
                        .setViewer(player)
                        .setGui(gui)
                        .setTitle("ワープ")
                        .build();

                window.open();
            } catch (UserNotFoundException e){
                player.sendMessage(Component.text("[テレポートAI] " + "プレイヤーが存在しないようです。 コード MV1").color(TextColor.color(Color.RED.asRGB())));
            } catch (MywarpNotFoundException e){
                player.sendMessage(Component.text("[テレポートAI] " + "ワープポイントが見つかりませんでした。コード MV2").color(TextColor.color(Color.RED.asRGB())));
            }
    }

}
