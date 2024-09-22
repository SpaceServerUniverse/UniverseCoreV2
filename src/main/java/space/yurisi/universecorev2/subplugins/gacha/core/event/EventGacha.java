package space.yurisi.universecorev2.subplugins.gacha.core.event;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.database.models.count.LifeCount;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.database.repositories.count.CountRepository;
import space.yurisi.universecorev2.database.repositories.count.LifeCountRepository;
import space.yurisi.universecorev2.exception.CountNotFoundException;
import space.yurisi.universecorev2.exception.LifeCountNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.item.UniverseItem;
import space.yurisi.universecorev2.subplugins.gacha.constrants.GachaRarity;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.PlayerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static space.yurisi.universecorev2.item.UniverseItem.removeItem;

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
    }

    public void turn() {
        Boolean ticket = UniverseItem.removeItem(player, "gacha_ticket");

        if(!ticket){
            Message.sendErrorMessage(player, "[ガチャAI]", "チケットが足りません。");
            return;
        }

        if(!PlayerState.hasInventorySpace(player)){
            Message.sendErrorMessage(player, "[ガチャAI]", "インベントリに空きがありません");
            return;
        }
        Random rand = new Random();
        int num = rand.nextInt(1000);

        ItemStack reward = null;

        if (num < 30 && !ultra_rare.isEmpty()) { // 3%
            reward = getRandomItem(ultra_rare);
            sendMessages(player, reward, GachaRarity.UltraRare);
        } else if (num < 80 && !super_rare.isEmpty()) { // 5%の確率
            reward = getRandomItem(super_rare);
            sendMessages(player, reward, GachaRarity.SuperRare);
        } else if (num < 480 && !rare.isEmpty()) { // 40%
            reward = getRandomItem(rare);
            sendMessages(player, reward, GachaRarity.Rare);
        } else if (!normal.isEmpty()) {
            reward = getRandomItem(normal);
            sendMessages(player, reward, GachaRarity.Normal);
        }

        if (reward == null) {
            Message.sendErrorMessage(player, "[ガチャAI]", "エラーが発生しました。");
            return;
        }

        player.getInventory().addItem(reward);

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
        rare.add(new ItemStack(Material.PUMPKIN_PIE, 32));
        rare.add(new ItemStack(Material.COOKED_RABBIT, 32));
        rare.add(new ItemStack(Material.ENCHANTED_GOLDEN_APPLE, 5));
        rare.add(new ItemStack(Material.GOLDEN_CARROT, 12));
        rare.add(new ItemStack(Material.COOKIE, 32));
    }

    protected void initializeSuperRareList() {

    }

    protected void initializeUltraRareList() {
    }

    protected ItemStack getErrorItem() {
        ItemStack item = ItemStack.of(Material.DIRT);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("§3エラーアイテム 運営に報告して下さい。"));
        item.setItemMeta(meta);
        return item;
    }

    protected void sendMessages(Player player, ItemStack itemStack, GachaRarity rarity) {
        ItemMeta meta = itemStack.getItemMeta();

        UserRepository userRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository();
        CountRepository countRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getCountRepository();
        LifeCountRepository lifeCountRepo = UniverseCoreV2API.getInstance().getDatabaseManager().getLifeCountRepository();

        try {
            User user = userRepository.getUserFromUUID(player.getUniqueId());
            Count count = countRepository.getCountFromUser(user);
            LifeCount lifeCount = lifeCountRepo.getLifeCount(count);

            if (rarity == GachaRarity.Normal || rarity == GachaRarity.Rare) {
                Message.sendSuccessMessage(player, "[ガチャAI]", "ガチャで" + itemStack.getType().toString() + "§rを入手しました！");
                lifeCount.setGacha_ceiling_count(lifeCount.getGacha_ceiling_count() + 1);
            }

            if (rarity == GachaRarity.SuperRare) {
                Message.sendSuccessMessage(player, "[ガチャAI]", "ガチャで" + meta.getDisplayName() + "§rを入手しました！");
                lifeCount.setGacha_rarity_count(lifeCount.getGacha_rarity_count() + 1);
                Bukkit.getServer().sendActionBar(Component.text("§eガチャで§b" + player.getName() + "§eが §cスーパーレア" + meta.getDisplayName() + "§cを引きました。§a(" + lifeCount.getGacha_ceiling_count() + "回目)"));
                lifeCount.setGacha_ceiling_count(0L);
            }

            if (rarity == GachaRarity.UltraRare) {
                Message.sendSuccessMessage(player, "[ガチャAI]", "ガチャで" + meta.getDisplayName() + "§rを入手しました！");
                lifeCount.setGacha_rarity_count(lifeCount.getGacha_rarity_count() + 1);
                Bukkit.getServer().sendActionBar(Component.text("§eガチャで§b" + player.getName() + "§eが §cスーパーレア" + meta.getDisplayName() + "§cを引きました。§a(" + lifeCount.getGacha_ceiling_count() + "回目)"));
                lifeCount.setGacha_ceiling_count(0L);
            }

            lifeCount.setGacha(lifeCount.getGacha() + 1);


            lifeCountRepo.updateLifeCount(lifeCount);

        } catch (UserNotFoundException userNotFoundException) {
            Message.sendErrorMessage(player, "[異常なエラー]", "ユーザーのデータが存在しませんでした。");
        } catch (CountNotFoundException e) {
            Message.sendErrorMessage(player, "[異常なエラー]", "カウントのデータが存在しませんでした。");
        } catch (LifeCountNotFoundException e) {
            Message.sendErrorMessage(player, "[異常なエラー]", "生活カウントのデータが存在しませんでした。");
        }
    }

    public List<ItemStack> getNormal() {
        return this.normal;
    }

    public List<ItemStack> getRare() {
        return this.rare;
    }

    public List<ItemStack> getSuperRare() {
        return this.super_rare;
    }

    public List<ItemStack> getUltraRare() {
        return this.ultra_rare;
    }
}
