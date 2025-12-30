package space.yurisi.universecorev2.subplugins.universeslot.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.exception.RollLengthNotEnoughException;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;

import java.util.*;

public class Roller {

    public static final int ROTATE_INTERVAL = 2;// タスク実行間隔（ティック数）

    private List<ItemStack> rotateItemsLane1;
    public List<ItemStack> getRotateItemsLane1() {
        return rotateItemsLane1;
    }
    public ItemStack getRandomRotateItem() {
        Random random = new Random();
        int randomIndex = random.nextInt(rotateItemsLane1.size());
        return rotateItemsLane1.get(randomIndex);
    }

    private List<ItemStack> rotateItemsLane2;
    public List<ItemStack> getRotateItemsLane2() {
        return rotateItemsLane2;
    }

    private List<ItemStack> rotateItemsLane3;
    public List<ItemStack> getRotateItemsLane3() {
        return rotateItemsLane3;
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
    }

    public void createRandomLane(int rollLength) throws RollLengthNotEnoughException{
        if(rollLength < rotateItems.size()){
            throw new RollLengthNotEnoughException("管理者に連絡してください。ロール長が不正です。" + rollLength);
        }
        // lan1,2,3まずrotateItemsからコピー
        rotateItemsLane1 = new ArrayList<>(rotateItems);
        rotateItemsLane2 = new ArrayList<>(rotateItems);
        rotateItemsLane3 = new ArrayList<>(rotateItems);

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
    }


}
