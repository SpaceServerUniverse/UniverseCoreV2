package space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_menu.item.DeleteBirthdayCardMenuItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_menu.item.SendBirthdayCardMenuItem;
import space.yurisi.universecorev2.subplugins.birthdaycard.menu.birthday_menu.item.ShowBirthdayCardMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class BirthdayCardMenu implements BaseMenu {
    public void sendMenu(Player player) {
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName(""));
        Gui gui = Gui.normal()
                .setStructure(
                        "# # # # # # # # # ",
                        "# # c p . . r # # ",
                        "# # # # # # # # # ")
                .addIngredient('#', border)
                .addIngredient('c', new ShowBirthdayCardMenuItem())
                .addIngredient('p', new SendBirthdayCardMenuItem())
                .addIngredient('r', new DeleteBirthdayCardMenuItem())
                .build();
        Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("BirthdayCard Menu")
                .build();
        window.open();
    }
}
