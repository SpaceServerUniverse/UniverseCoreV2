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

    private List<ItemStack> rotateItems;
    public List<ItemStack> getRotateItems() {
        return rotateItems;
    }
    public ItemStack getRandomRotateItem() {
        int randomIndex = (int) (Math.random() * rotateItems.size());
        return rotateItems.get(randomIndex);
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
        rotateItems = List.of(
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
    }


}
