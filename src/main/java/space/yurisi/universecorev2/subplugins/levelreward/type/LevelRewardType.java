package space.yurisi.universecorev2.subplugins.levelreward.type;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.item.repair_cream.RepairCream;
import space.yurisi.universecorev2.item.ticket.GachaTicket;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.List;
import java.util.Objects;

public class LevelRewardType {
    public enum LevelReward {

        REPAIR_CREAM("修復クリーム", Material.MAGMA_CREAM) {
            @Override
            public ItemStack createRewardItem() {
                ItemStack item = Objects.requireNonNull(UniverseItem.getItem(RepairCream.id)).getItem().clone();
                item.setAmount(5);
                return item;
            }
        },

        GACHATICKET_100("§eガチャチケット100枚", Material.PAPER) {
            @Override
            public ItemStack createRewardItem() {
                ItemStack item = Objects.requireNonNull(UniverseItem.getItem(GachaTicket.id)).getItem().clone();
                item.setAmount(100);
                return item;
            }
        },

        GACHATICKET_200("§eガチャチケット200枚", Material.PAPER) {
            @Override
            public ItemStack createRewardItem() {
                ItemStack item = Objects.requireNonNull(UniverseItem.getItem(GachaTicket.id)).getItem().clone();
                item.setAmount(200);
                return item;
            }
        },

        ERROR("エラー", Material.GRAY_STAINED_GLASS_PANE) {
            @Override
            public ItemStack createRewardItem() {
                return new ItemStack(Material.BARRIER);
            }
        };

        private final String displayName;
        private final Material guiMaterial;

        LevelReward(String displayName, Material guiMaterial) {
            this.displayName = displayName;
            this.guiMaterial = guiMaterial;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Material getGuiMaterial() {
            return guiMaterial;
        }

        public abstract ItemStack createRewardItem();

        public ItemBuilder createGuiItem(int level) {
            return new ItemBuilder(guiMaterial)
                    .setDisplayName("§cLevel" + level + "Bonus")
                    .setLegacyLore(List.of(displayName));
        }
    }

    public LevelReward rewardBy(int level) {
        return switch (level) {
            case 50, 150, 250, 350, 450, 550, 650, 750, 850, 950 -> LevelReward.REPAIR_CREAM;
            case 100, 200, 300, 400, 600, 700, 800, 900 ->LevelReward.GACHATICKET_100;
            case 500, 1000 -> LevelReward.GACHATICKET_200;
            default -> LevelReward.ERROR;
        };
    }
}
