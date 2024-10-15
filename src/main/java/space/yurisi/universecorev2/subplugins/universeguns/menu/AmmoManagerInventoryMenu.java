package space.yurisi.universecorev2.subplugins.universeguns.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.universeguns.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universeguns.menu.item.*;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

public class AmmoManagerInventoryMenu implements BaseMenu {

    private final UniverseCoreAPIConnector connector;

    public AmmoManagerInventoryMenu(UniverseCoreAPIConnector connector) {
        this.connector = connector;
    }

    public void sendMenu(Player player) {
        // send menu
        try{
            if(!this.connector.isExistsAmmoData(player)){
                this.connector.AmmoDataInit(player);
            }

            Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));
            Item iron1 = new SimpleItem(new ItemBuilder(Material.IRON_INGOT).setAmount(1));
            Item iron2 = new SimpleItem(new ItemBuilder(Material.IRON_INGOT).setAmount(2));
            Item iron3 = new SimpleItem(new ItemBuilder(Material.IRON_INGOT).setAmount(3));
            Item iron4 = new SimpleItem(new ItemBuilder(Material.IRON_INGOT).setAmount(4));
            Item powder1 = new SimpleItem(new ItemBuilder(Material.GUNPOWDER).setAmount(1));
            Item powder2 = new SimpleItem(new ItemBuilder(Material.GUNPOWDER).setAmount(2));
            Item powder3 = new SimpleItem(new ItemBuilder(Material.GUNPOWDER).setAmount(3));
            Item powder4 = new SimpleItem(new ItemBuilder(Material.GUNPOWDER).setAmount(4));
            Item powder5 = new SimpleItem(new ItemBuilder(Material.GUNPOWDER).setAmount(5));
            Gui.Builder.@NotNull Normal gui = Gui.normal()
                    .setStructure(
                            "# a b c d e f g #",
                            "# # # # # # # # #",
                            "# i i j k j k l #",
                            "# p q q r r s t #")
                    .addIngredient('#', border)
                    .addIngredient('i', iron1)
                    .addIngredient('j', iron2)
                    .addIngredient('k', iron3)
                    .addIngredient('l', iron4)
                    .addIngredient('p', powder1)
                    .addIngredient('q', powder2)
                    .addIngredient('r', powder3)
                    .addIngredient('s', powder4)
                    .addIngredient('t', powder5)

                    .addIngredient('a', new HandGunAmmoItem(this.connector, 30, player))
                    .addIngredient('b', new SubMachineGunAmmoItem(this.connector, 60, player))
                    .addIngredient('c', new AssaultRifleAmmoItem(this.connector, 60, player))
                    .addIngredient('d', new SniperRifleAmmoItem(this.connector, 24, player))
                    .addIngredient('e', new ShotGunAmmoItem(this.connector, 30, player))
                    .addIngredient('f', new LightMachineGunAmmoItem(this.connector, 80, player))
                    .addIngredient('g', new ExplosiveAmmoItem(this.connector, 12, player));

            xyz.xenondevs.invui.window.Window window = Window.single()
                    .setViewer(player)
                    .setGui(gui.build())
                    .setTitle("弾薬バッグ")
                    .build();

            window.open();

        } catch (UserNotFoundException e) {
            Message.sendErrorMessage(player, "[武器AI]", "ユーザーが見つかりませんでした。");
        }
    }
}
