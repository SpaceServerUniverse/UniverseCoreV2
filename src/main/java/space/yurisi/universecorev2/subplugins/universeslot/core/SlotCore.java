package space.yurisi.universecorev2.subplugins.universeslot.core;

import org.bukkit.Location;
import org.bukkit.block.Shelf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotRotateManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotStatusManager;

import java.util.List;

public class SlotCore {

    private Player player;

    private Shelf shelf;

    private int currentIndexSlot1;
    private int currentIndexSlot2;
    private int currentIndexSlot3;

    private BukkitRunnable rotateTaskSlot1;
    private BukkitRunnable rotateTaskSlot2;
    private BukkitRunnable rotateTaskSlot3;

    private PlayerStatusManager playerStatusManager;
    private SlotStatusManager slotStatusManager;

    private Location location;
    public Location getLocation() {
        return location;
    }

    public SlotCore(Player player, Shelf shelf) {
        this.player = player;
        this.shelf = shelf;
        playerStatusManager = UniverseSlot.getInstance().getPlayerStatusManager();
        slotStatusManager = UniverseSlot.getInstance().getSlotStatusManager();
        this.location = shelf.getLocation();
    }

    public boolean startSlot(){
        if(playerStatusManager.hasFlag(player.getUniqueId(), PlayerStatusManager.ON_SLOT)){
            return false;
        }
        if(slotStatusManager.isInUse(location)){
            return false;
        }
        slotStatusManager.addFlag(location, SlotStatusManager.IN_USE);
        playerStatusManager.addFlag(player.getUniqueId(), PlayerStatusManager.ON_SLOT);
        List<ItemStack> rotateItems = UniverseSlot.getInstance().getSlotRotateManager().getRotateItems();
        currentIndexSlot1 = rotateItems.indexOf(shelf.getInventory().getItem(0));
        currentIndexSlot2 = rotateItems.indexOf(shelf.getInventory().getItem(1));
        currentIndexSlot3 = rotateItems.indexOf(shelf.getInventory().getItem(2));

        rotateTaskSlot1 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlot1 = (currentIndexSlot1 + 1) % rotateItems.size();
                shelf.getInventory().setItem(0, rotateItems.get(currentIndexSlot1));
            }
        };
        rotateTaskSlot2 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlot2 = (currentIndexSlot2 + 1) % rotateItems.size();
                shelf.getInventory().setItem(1, rotateItems.get(currentIndexSlot2));
            }
        };
        rotateTaskSlot3 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlot3 = (currentIndexSlot3 + 1) % rotateItems.size();
                shelf.getInventory().setItem(2, rotateItems.get(currentIndexSlot3));
            }
        };

        rotateTaskSlot1.runTaskTimer(UniverseCoreV2.getInstance(), 0L, SlotRotateManager.ROTATE_INTERVAL);
        rotateTaskSlot2.runTaskTimer(UniverseCoreV2.getInstance(), 2L, SlotRotateManager.ROTATE_INTERVAL);
        rotateTaskSlot3.runTaskTimer(UniverseCoreV2.getInstance(), 4L, SlotRotateManager.ROTATE_INTERVAL);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE1_SPINNING);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE2_SPINNING);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE3_SPINNING);

        return true;
    }

    public void stopSlot(int selectedLane){
        if(!slotStatusManager.isLaneSpinning(location, selectedLane)){
            return;
        }
        switch (selectedLane){
            case 1 -> {
                rotateTaskSlot1.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE1_SPINNING);
            }
            case 2 -> {
                rotateTaskSlot2.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE2_SPINNING);
            }
            case 3 -> {
                rotateTaskSlot3.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE3_SPINNING);
            }
        }
        if(slotStatusManager.getSpinningLaneCount(location) == 0){
            resultSlot();
        }
    }

    public void resultSlot(){
        ItemStack item1 = shelf.getInventory().getItem(0);
        ItemStack item2 = shelf.getInventory().getItem(1);
        ItemStack item3 = shelf.getInventory().getItem(2);
        // 結果処理をここに実装
        // とりあえず全部揃ってるか判定
        if(item1 != null && item2 != null && item3 != null &&
           item1.isSimilar(item2) && item2.isSimilar(item3)){
            // プレイヤーに報酬を与えるなどの処理
            player.sendMessage("§aおめでとう！スロットが揃いました！");
        } else {
            player.sendMessage("§c残念！スロットが揃いませんでした。");
        }
        stopSlotMachine();
    }

    public void stopSlotMachine(){
        rotateTaskSlot1.cancel();
        rotateTaskSlot2.cancel();
        rotateTaskSlot3.cancel();
        slotStatusManager.removeFlag(location, SlotStatusManager.IN_USE);
        slotStatusManager.removeFlag(location, SlotStatusManager.LANE1_SPINNING);
        slotStatusManager.removeFlag(location, SlotStatusManager.LANE2_SPINNING);
        slotStatusManager.removeFlag(location, SlotStatusManager.LANE3_SPINNING);
        playerStatusManager.removeFlag(player.getUniqueId(), PlayerStatusManager.ON_SLOT);
    }

}
