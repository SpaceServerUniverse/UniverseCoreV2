package space.yurisi.universecorev2.subplugins.levelreward.menu.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.LevelReward;
import space.yurisi.universecorev2.database.repositories.LevelRewardRepository;
import space.yurisi.universecorev2.exception.LevelRewardNotFoundException;
import space.yurisi.universecorev2.subplugins.levelreward.menu.LevelRewardMenu;
import space.yurisi.universecorev2.subplugins.levelreward.type.LevelRewardType;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;
import space.yurisi.universecorev2.subplugins.receivebox.ReceiveBoxAPI;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.Sound;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;
import xyz.xenondevs.invui.item.impl.SimpleItem;

import java.util.Calendar;
import java.util.List;

public class LevelRewardItem extends AbstractItem {

    int level;
    boolean received;
    LevelRewardRepository levelRewardRepository;

    LevelRewardType levelRewardType;

    public LevelRewardItem(int level, boolean received) {
        this.level = level;
        this.received = received;
        this.levelRewardRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(LevelRewardRepository.class);
        this.levelRewardType = new LevelRewardType();
    }

    @Override
    public ItemProvider getItemProvider() {
        if (received) {
            return new ItemBuilder(Material.GRAY_STAINED_GLASS)
                    .setDisplayName("§cLevel" + level + "Bonus")
                    .setLegacyLore(List.of("受け取り済み"));
        }
        ;
        return levelRewardType.rewardBy(level).createGuiItem(level);
    }

    @Override
    public void handleClick(@NotNull ClickType clickType,
                            @NotNull Player player,
                            @NotNull InventoryClickEvent event) {


        try {
            if(LevelSystemAPI.getInstance().getLevel(player) < level){
                Message.sendErrorMessage(player, "[報酬AI]", "まだ受け取れません。");
                Sound.sendErrorSound(player);
                return;
            }
        } catch (PlayerDataNotFoundException e) {
            Message.sendErrorMessage(player, "[報酬AI]", "エラーが発生しました。errorcode:1");
            Sound.sendErrorSound(player);
            return;
        }

        LevelReward levelReward;

        try {
            levelReward = levelRewardRepository.findByPlayer(player, level);
            if (levelReward.getIs_received()) {
                Message.sendErrorMessage(player, "[報酬AI]", "すでに受け取っています。");
                Sound.sendErrorSound(player);
                return;
            }
        } catch (LevelRewardNotFoundException e) {
            levelReward = levelRewardRepository.create(player, level);
        }

        ItemStack item = levelRewardType.rewardBy(level).createRewardItem();

        if (item == null || item.getType() == Material.BARRIER) {
            Message.sendErrorMessage(player, "[報酬AI]", "エラーが発生しました。errorcode:2");
            Sound.sendErrorSound(player);
            return;
        }

        levelReward.setIs_received(true);
        levelRewardRepository.update(levelReward);

        Calendar expireDate = Calendar.getInstance();
        expireDate.add(Calendar.MONTH, 1);

        ReceiveBoxAPI.AddReceiveItem(item, player.getUniqueId(), expireDate.getTime(), "レベル報酬");
        Message.sendSuccessMessage(player, "[報酬AI]", "報酬は受け取りボックスに送られました！");
        Sound.sendSuccessSound(player);

        LevelRewardMenu levelRewardMenu = new LevelRewardMenu();
        levelRewardMenu.sendMenu(player);
    }
}
