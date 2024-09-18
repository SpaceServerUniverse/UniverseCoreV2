package space.yurisi.universecorev2.subplugins.tppsystem.menu.receive_request_menu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.tppsystem.TPPSystem;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.menu_item.*;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.receive_request_menu.item.*;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReceiveRequestInventoryMenu implements BaseMenu {

    private final RequestManager requestManager;

    private final UniverseCoreAPIConnector connector;

    public ReceiveRequestInventoryMenu(RequestManager requestManager, UniverseCoreAPIConnector connector){
        this.requestManager = requestManager;
        this.connector = connector;
    }

    public void sendMenu(Player player){
        if (!this.requestManager.hasReceivedRequest(player)){
            player.sendMessage("現在受信しているリクエストはありません。");
            return;
        }
        try{
            List<UUID> requests = this.requestManager.getRequest(player);
            requestManager.updateRequest(player);
            List<Item> items = requests.stream()
                    .map(user ->  new ReceiveRequestMenuItem(player, this.requestManager, user))
                    .collect(Collectors.toList());

            Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));

            Gui gui = PagedGui.items()
                    .setStructure(
                            "# # # # # # # # #",
                            "# x x x x x x x #",
                            "# x x x x x x x #",
                            "# x x x x x x x #",
                            "# # # < b > # # #")
                    .addIngredient('#', border)
                    .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                    .addIngredient('b', new TopItem(this.requestManager, this.connector))
                    .addIngredient('<', new BackItem())
                    .addIngredient('>', new ForwardItem())
                    .setContent(items)
                    .build();

            xyz.xenondevs.invui.window.Window window = Window.single()
                    .setViewer(player)
                    .setGui(gui)
                    .setTitle("Request")
                    .build();

            window.open();
        } catch (Exception e) {
            player.sendMessage(Component.text("現在オンラインのユーザーが見つかりませんでした。", TextColor.color(Color.RED.asRGB())));
        }
    }
}

