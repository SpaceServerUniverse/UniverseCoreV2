package space.yurisi.universecorev2.subplugins.universeslot.core;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import space.yurisi.universecorev2.exception.RollLengthNotEnoughException;
import space.yurisi.universecorev2.subplugins.universeslot.manager.RoleManager;

import java.util.*;

public class Roller {

    public static final long YURISI_AWARD = 1000L; // ゆりし賞金
    public static final long MEYASON_AWARD = 500L; // めやそん賞金
    public static final long DIAMOND_AWARD = 100L; // ダイヤ賞金
    public static final long BELL_AWARD = 80L; // ベル賞金
    public static final long GLOW_BERRIES_AWARD = 40L; // グロウベリー賞金
    public static final long SWEET_BERRIES_AWARD = 30L; // スイートベリー賞金
    public static final long COD_AWARD = 15L; // タラ賞金
    public static final long GREEN_BUNDLE_AWARD = 10L; // 緑のバンドル賞金
    public static final long ENDER_DRAGON_HEAD_AWARD = 5000L;
    public static final int ROTATE_INTERVAL = 2;// タスク実行間隔（ティック数）

    public ItemStack getRandomRotateItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(rotateItems.size());
        return rotateItems.get(randomIndex);
    }

    private HashMap<RoleManager.SlotRole, ItemStack> roleItemMap;

    public ItemStack getItemFromRole(RoleManager.SlotRole role) {
        return roleItemMap.get(role);
    }

    private List<ItemStack> rotateItems;
    public List<ItemStack> getRotateItems() {
        return rotateItems;
    }

    public Roller() {
        ItemStack yurisiHead = new ItemStack(Material.PLAYER_HEAD);
        ItemStack meyasonHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta yurisiSkullMeta = (SkullMeta) yurisiHead.getItemMeta();
        SkullMeta meyasonSkullMeta = (SkullMeta) meyasonHead.getItemMeta();
        OfflinePlayer yurisi = Bukkit.getOfflinePlayer("yurisi");
        OfflinePlayer meyason = Bukkit.getOfflinePlayer("Villagermeyason");
        yurisiSkullMeta.setOwningPlayer(yurisi);
        meyasonSkullMeta.setOwningPlayer(meyason);
        yurisiHead.setItemMeta(yurisiSkullMeta);
        meyasonHead.setItemMeta(meyasonSkullMeta);
        rotateItems = List.of(
                yurisiHead,
                meyasonHead,
                new ItemStack(Material.DIAMOND),
                new ItemStack(Material.BELL),
                new ItemStack(Material.COD),
                new ItemStack(Material.SWEET_BERRIES),
                new ItemStack(Material.GLOW_BERRIES),
                new ItemStack(Material.GREEN_BUNDLE)
        );
        roleItemMap = new HashMap<>();
        roleItemMap.put(RoleManager.SlotRole.YURISI_HEAD, yurisiHead);
        roleItemMap.put(RoleManager.SlotRole.MEYASON_HEAD, meyasonHead);
        roleItemMap.put(RoleManager.SlotRole.DIAMOND, new ItemStack(Material.DIAMOND));
        roleItemMap.put(RoleManager.SlotRole.BELL, new ItemStack(Material.BELL));
        roleItemMap.put(RoleManager.SlotRole.COD, new ItemStack(Material.COD));
        roleItemMap.put(RoleManager.SlotRole.SWEET_BERRIES, new ItemStack(Material.SWEET_BERRIES));
        roleItemMap.put(RoleManager.SlotRole.GLOW_BERRIES, new ItemStack(Material.GLOW_BERRIES));
        roleItemMap.put(RoleManager.SlotRole.GREEN_BUNDLE, new ItemStack(Material.GREEN_BUNDLE));
        roleItemMap.put(RoleManager.SlotRole.MISS, new ItemStack(Material.BARRIER));
    }

    public List<List<ItemStack>> createRandomLane(int rollLength) throws RollLengthNotEnoughException{
        if(rollLength < rotateItems.size()){
            throw new RollLengthNotEnoughException("管理者に連絡してください。ロール長が不正です。" + rollLength);
        }
        // lan1,2,3まずrotateItemsからコピー
        List<ItemStack> rotateItemsLane1 = new ArrayList<>(rotateItems);
        List<ItemStack> rotateItemsLane2 = new ArrayList<>(rotateItems);
        List<ItemStack> rotateItemsLane3 = new ArrayList<>(rotateItems);

        // rollLengthまでplayer_head以外のアイテムをランダムに追加
        for(int i = rotateItemsLane1.size(); i < rollLength; i++){
            ItemStack randomItem1 = rotateItems.get(new Random().nextInt(rotateItems.size() - 2) + 2);
            ItemStack randomItem2 = rotateItems.get(new Random().nextInt(rotateItems.size() - 2) + 2);
            ItemStack randomItem3 = rotateItems.get(new Random().nextInt(rotateItems.size() - 2) + 2);
            rotateItemsLane1.add(randomItem1);
            rotateItemsLane2.add(randomItem2);
            rotateItemsLane3.add(randomItem3);
        }

        // それぞれリストをシャッフル
        Collections.shuffle(rotateItemsLane1);
        Collections.shuffle(rotateItemsLane2);
        Collections.shuffle(rotateItemsLane3);
        return List.of(rotateItemsLane1, rotateItemsLane2, rotateItemsLane3);
    }

    public List<ItemStack> createFreezeLane(int rollLength) throws RollLengthNotEnoughException{
        if(rollLength < rotateItems.size()){
            throw new RollLengthNotEnoughException("管理者に連絡してください。ロール長が不正です。" + rollLength);
        }

        List<ItemStack> rotateItemsLane = new ArrayList<>();

        rotateItemsLane.add(roleItemMap.get(RoleManager.SlotRole.YURISI_HEAD));

        for(int i = 1; i < rollLength; i++){
            rotateItemsLane.add(ItemStack.of(Material.DRAGON_HEAD));
        }

        return rotateItemsLane;
    }


}
