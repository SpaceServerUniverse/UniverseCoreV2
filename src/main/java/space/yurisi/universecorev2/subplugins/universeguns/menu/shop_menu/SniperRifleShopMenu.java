package space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.gun.*;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu.item.GunShopMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class SniperRifleShopMenu implements BaseMenu{

    public SniperRifleShopMenu() {
    }

    public void sendMenu(Player player) {

        Item border = new SimpleItem(new ItemBuilder(Material.AIR));

        Gui.Builder.@NotNull Normal gui = Gui.normal()
                .setStructure(
                        "a # # # # # # # #")
                .addIngredient('#', border)

                .addIngredient('a', new GunShopMenuItem(new L96A1()));

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui.build())
                .setTitle("スナイパーライフル")
                .build();

        window.open();
    }
}