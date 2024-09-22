package space.yurisi.universecorev2.subplugins.gacha.core.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class EventGacha {

    protected Player player;

    protected List<ItemStack> ultra_rare = new ArrayList<>();
    protected List<ItemStack> super_rare = new ArrayList<>();
    protected List<ItemStack> rare = new ArrayList<>();
    protected List<ItemStack> normal = new ArrayList<>();

    public EventGacha(Player player) {
        this.player = player;
        initializeNormalList();
        initializeRareList();
        initializeSuperRareList();
        initializeUltraRareList();

        turn();
    }

    public void turn() {
        Random rand = new Random();
        int num = rand.nextInt(1000);


        ItemStack reward = null;

        if (num < 30 && !ultra_rare.isEmpty()) { // 3%
            reward = getRandomItem(ultra_rare);
        } else if (num < 80 && !super_rare.isEmpty()) { // 5%の確率
            reward = getRandomItem(super_rare);
        } else if (num < 480 && !rare.isEmpty()) { // 40%
            reward = getRandomItem(rare);
        } else if (!normal.isEmpty()) {
            reward = getRandomItem(normal);
        }

        if (reward == null) {
            Message.sendErrorMessage(player, "[ガチャAI]", "エラーが発生しました。");
            return;
        }

        player.getInventory().addItem(reward);
        player.sendMessage("ガチャでアイテムを入手しました！");
    }

    private ItemStack getRandomItem(List<ItemStack> itemList) {
        Random rand = new Random();
        return itemList.get(rand.nextInt(itemList.size()));
    }

    protected void initializeNormalList() {
        normal.add(new ItemStack(Material.OAK_LOG, 32));
        normal.add(new ItemStack(Material.SPRUCE_LOG, 32));
        normal.add(new ItemStack(Material.BIRCH_LOG, 32));
        normal.add(new ItemStack(Material.JUNGLE_LOG, 32));
        normal.add(new ItemStack(Material.ACACIA_LOG, 32));
        normal.add(new ItemStack(Material.DARK_OAK_LOG, 32));
    }

    protected void initializeRareList() {
        rare.add(new ItemStack(Material.COOKED_BEEF, 32));
        rare.add(new ItemStack(Material.PUMPKIN_PIE,32));
        rare.add(new ItemStack(Material.COOKED_RABBIT,32));
        rare.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 5));
        rare.add(new ItemStack(Material.GOLDEN_CARROT, 12));
        rare.add(new ItemStack(Material.COOKIE, 32));
    }

    protected void initializeSuperRareList(){

    }

    protected void initializeUltraRareList() {
    }

    protected ItemStack getErrorItem(){
        ItemStack item = ItemStack.of(Material.DIRT);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("§3エラーアイテム 運営に報告して下さい。"));
        item.setItemMeta(meta);
        return item;
    }
}
