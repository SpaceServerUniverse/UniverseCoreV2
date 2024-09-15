package space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.models.AutoTppSetting;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.tppsystem.manager.RequestManager;
import space.yurisi.universecorev2.subplugins.tppsystem.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.tppsystem.menu.tpp_system_menu.item.*;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class TPPSystemInventoryMenu implements BaseMenu {

    private final RequestManager requestManager;

    private final UniverseCoreAPIConnector connector;

    public TPPSystemInventoryMenu(RequestManager requestManager, UniverseCoreAPIConnector connector) {
        this.requestManager = requestManager;
        this.connector = connector;
    }

    public void sendMenu(Player player) {
        try {
            if (!connector.isExistsAutoTPPSetting(player)) {
                connector.createAutoTPPSetting(player);
            }

            AutoTppSetting autoTppSetting = connector.getAutoTPPSettingFromPlayer(player);

            Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
            Gui.Builder.@NotNull Normal gui = Gui.normal()
                    .setStructure(
                            "# # # # # # # # #",
                            "# # a b c d . # #",
                            "# # # # # # # # #")
                    .addIngredient('#', border)
                    .addIngredient('a', new SendTPPMenuItem(this.requestManager, this.connector))
                    .addIngredient('b', new DeleteTPPMenuItem(player, this.requestManager))
                    .addIngredient('c', new ReceiveTPPMenuItem(this.requestManager, this.connector));
                    if(autoTppSetting.getIs_auto_accept()) {
                        gui.addIngredient('d', new AutoDeniedTPPMenuItem(this.connector));
                    }else{
                        gui.addIngredient('d', new AutoAcceptTPPMenuItem(this.connector));
                    }


            xyz.xenondevs.invui.window.Window window = Window.single()
                    .setViewer(player)
                    .setGui(gui.build())
                    .setTitle("TPP")
                    .build();

            window.open();
        } catch (UserNotFoundException e) {
            player.sendMessage("エラー: ユーザーデータが見つかりませんでした。");
        }
    }

}
