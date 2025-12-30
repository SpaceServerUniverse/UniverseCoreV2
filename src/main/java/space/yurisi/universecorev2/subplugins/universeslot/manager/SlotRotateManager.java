package space.yurisi.universecorev2.subplugins.universeslot.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;

import java.util.HashMap;
import java.util.List;

public class SlotRotateManager {

    private UniverseSlot main;

    private HashMap<Location, List<BukkitRunnable>> rotateTasks = new HashMap<>();

    public static final int ROTATE_INTERVAL = 2;// タスク実行間隔（ティック数）

    private List<ItemStack> rotateItemsLane1;
    public List<ItemStack> getRotateItemsLane1() {
        return rotateItemsLane1;
    }
    public ItemStack getRandomRotateItem() {
        int randomIndex = (int) (Math.random() * rotateItemsLane1.size());
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

    public SlotRotateManager(UniverseSlot main) {
        this.main = main;
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
        rotateItemsLane1 = List.of(
                yurisiHead,
                new ItemStack(Material.DIAMOND),
                new ItemStack(Material.BELL),
                new ItemStack(Material.COD),
                new ItemStack(Material.SWEET_BERRIES),
                meyasonHead,
                new ItemStack(Material.BELL),
                new ItemStack(Material.SWEET_BERRIES),
                new ItemStack(Material.DIAMOND),
                new ItemStack(Material.COD),
                new ItemStack(Material.GLOW_BERRIES)
        );
        rotateItemsLane2 = List.of(
                new ItemStack(Material.GLOW_BERRIES),
                yurisiHead,
                new ItemStack(Material.COD),
                new ItemStack(Material.COD),
                new ItemStack(Material.SWEET_BERRIES),
                new ItemStack(Material.BELL),
                new ItemStack(Material.GLOW_BERRIES),
                meyasonHead,
                new ItemStack(Material.DIAMOND),
                new ItemStack(Material.COD),
                new ItemStack(Material.DIAMOND)
        );
        rotateItemsLane3 = List.of(
                new ItemStack(Material.GLOW_BERRIES),
                new ItemStack(Material.COD),
                meyasonHead,
                new ItemStack(Material.BELL),
                new ItemStack(Material.SWEET_BERRIES),
                new ItemStack(Material.DIAMOND),
                new ItemStack(Material.GLOW_BERRIES),
                new ItemStack(Material.COD),
                new ItemStack(Material.BELL),
                new ItemStack(Material.SWEET_BERRIES),
                yurisiHead
        );

    }


}
