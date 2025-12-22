package space.yurisi.universecorev2.subplugins.levelreward.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.LevelReward;
import space.yurisi.universecorev2.database.repositories.LevelRewardRepository;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.levelreward.menu.item.LevelRewardItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;

public class LevelRewardMenu implements BaseMenu {

    String charPools = "ABCDEFGHIJKLMNOPQRSTUVWXYZ012345678";

    private List<Item> currentItems;

    @Override
    public void sendMenu(Player player) {
        currentItems = new ArrayList<>();
        Item border = new SimpleItem(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE));

        //データを先に取得
        LevelRewardRepository levelRewardRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(LevelRewardRepository.class);
        List<LevelReward> levelRewards = levelRewardRepository.getAllByPlayer(player);

        for (int i = 50; i <= 1000; i = i + 50) {
            int finalI = i;
            boolean received = levelRewards.stream().anyMatch(levelReward -> levelReward.getLevel() == finalI && levelReward.isReceived());
            currentItems.add(new LevelRewardItem(i, received));
        }

        Gui.Builder.Normal gui = Gui.normal()
                .setStructure(

                        "# A B C D E F G #",
                        "# H I J K L M N #",
                        "# O P Q R S T # #",
                        "# # # # # # # # #"
                )
                .addIngredient('#', border);

        for (int i = 0; i < charPools.length() && i < currentItems.size(); i++) {
            gui.addIngredient(charPools.charAt(i), currentItems.get(i));
        }

        Window.single()
                .setViewer(player)
                .setGui(gui.build())
                .setTitle("レベル報酬")
                .build()
                .open();
    }
}
