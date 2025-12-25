package space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.item.gun.F2;
import space.yurisi.universecorev2.item.gun.R4C;
import space.yurisi.universecorev2.item.gun.TacticalLeggings;
import space.yurisi.universecorev2.item.gun.TacticalVest;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu.item.ArmorShopMenuItem;
import space.yurisi.universecorev2.subplugins.universeguns.menu.shop_menu.item.GunShopMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class ArmorShopMenu implements BaseMenu {

    public ArmorShopMenu() {
    }

    public void sendMenu(Player player) {

        Item border = new SimpleItem(new ItemBuilder(Material.AIR));

        Gui.Builder.@NotNull Normal gui = Gui.normal()
                .setStructure(
                        "a b # # # # # # #")
                .addIngredient('#', border)

                .addIngredient('a', new ArmorShopMenuItem(TacticalVest.id))
                .addIngredient('b', new ArmorShopMenuItem(TacticalLeggings.id));


        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui.build())
                .setTitle("タクティカル装備ショップ")
                .build();

        window.open();
    }
}
