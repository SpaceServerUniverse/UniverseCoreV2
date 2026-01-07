package space.yurisi.universecorev2.subplugins.universeslot.core;

import org.bukkit.*;
import org.bukkit.block.Shelf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.LaneNumberWrongException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotStatusManager;
import space.yurisi.universecorev2.utils.Message;

import java.util.List;
import java.util.UUID;

public class SlotCore {

    private final Player player;
    private final UUID uuid;
    private final UUID ownerUUID;

    private final Shelf shelf;

    private int currentIndexSlot1;
    private int currentIndexSlot2;
    private int currentIndexSlot3;

    private BukkitRunnable rotateTaskSlot1;
    private BukkitRunnable rotateTaskSlot2;
    private BukkitRunnable rotateTaskSlot3;

    private final PlayerStatusManager playerStatusManager;
    private final SlotStatusManager slotStatusManager;

    private final Location location;
    public Location getLocation() {
        return location;
    }

    public SlotCore(Player player, Shelf shelf) {
        this.player = player;
        this.uuid = player.getUniqueId();
        this.shelf = shelf;
        playerStatusManager = UniverseSlot.getInstance().getPlayerStatusManager();
        slotStatusManager = UniverseSlot.getInstance().getSlotStatusManager();
        this.ownerUUID = UniverseSlot.getInstance().getSlotLocationManager().getOwnerUUID(shelf.getLocation());
        this.location = shelf.getLocation();
    }

    public boolean startSlot(){
        if(playerStatusManager.hasFlag(uuid, PlayerStatusManager.ON_SLOT)){
            return false;
        }
        if(slotStatusManager.isInUse(location)){
            return false;
        }
        if(player.getVehicle() == null){
            Message.sendErrorMessage(player, "[スロットAI]", "椅子に座ってスロットを引いてください。");
            return false;
        }
        try{
            UniverseEconomyAPI.getInstance().reduceMoney(player, 10L, "スロット利用料");
            if(!player.getUniqueId().equals(ownerUUID)) {
                Long user_id;
                Money money;
                UserRepository userRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository();
                MoneyRepository moneyRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getMoneyRepository();
                user_id = userRepository.getPrimaryKeyFromUUID(ownerUUID);
                money = moneyRepository.getMoneyFromUserId(user_id);
                money.setMoney(money.getMoney() + 2L);
                moneyRepository.updateMoney(money, 2L, "スロット利用料収益");
            }
        } catch (UserNotFoundException | MoneyNotFoundException e){
            Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報が見つかりません。スロットを利用できません。");
            return false;
        } catch (CanNotReduceMoneyException e){
            Message.sendErrorMessage(player, "[スロットAI]", "お金が足りないためスロットを利用できません。");
            return false;
        } catch (ParameterException e){
            Message.sendErrorMessage(player, "[スロットAI]", "エラー:ParameterExceptionが発生しました。運営にお問い合わせください。");
            return false;
        }
        slotStatusManager.addFlag(location, SlotStatusManager.IN_USE);
        playerStatusManager.addFlag(uuid, PlayerStatusManager.ON_SLOT);

        // ロール作成
        UniverseSlot.getInstance().getRoller().createRandomLane(15);
        List<ItemStack> rotateItemsLane1 = UniverseSlot.getInstance().getRoller().getRotateItemsLane1();
        List<ItemStack> rotateItemsLane2 = UniverseSlot.getInstance().getRoller().getRotateItemsLane2();
        List<ItemStack> rotateItemsLane3 = UniverseSlot.getInstance().getRoller().getRotateItemsLane3();

        currentIndexSlot1 = rotateItemsLane1.indexOf(shelf.getInventory().getItem(0));
        currentIndexSlot2 = rotateItemsLane2.indexOf(shelf.getInventory().getItem(1));
        currentIndexSlot3 = rotateItemsLane3.indexOf(shelf.getInventory().getItem(2));

        rotateTaskSlot1 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlot1 = (currentIndexSlot1 + 1) % rotateItemsLane1.size();
                shelf.getInventory().setItem(0, rotateItemsLane1.get(currentIndexSlot1));
            }
        };
        rotateTaskSlot2 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlot2 = (currentIndexSlot2 + 1) % rotateItemsLane2.size();
                shelf.getInventory().setItem(1, rotateItemsLane2.get(currentIndexSlot2));
            }
        };
        rotateTaskSlot3 = new BukkitRunnable() {
            @Override
            public void run() {
                if(player.getVehicle() == null){
                    Message.sendErrorMessage(player, "[スロットAI]", "椅子から降りたためスロットを強制終了します。");
                    stopSlotMachine();
                    return;
                }
                currentIndexSlot3 = (currentIndexSlot3 + 1) % rotateItemsLane3.size();
                shelf.getInventory().setItem(2, rotateItemsLane3.get(currentIndexSlot3));
            }
        };

        rotateTaskSlot1.runTaskTimer(UniverseCoreV2.getInstance(), 0L, Roller.ROTATE_INTERVAL);
        rotateTaskSlot2.runTaskTimer(UniverseCoreV2.getInstance(), 2L, Roller.ROTATE_INTERVAL);
        rotateTaskSlot3.runTaskTimer(UniverseCoreV2.getInstance(), 4L, Roller.ROTATE_INTERVAL);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE1_SPINNING);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE2_SPINNING);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE3_SPINNING);

        location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_HARP, 0.8f, 1.0f);

        return true;
    }

    public void stopSlot(int selectedLane){
        try {
            if (!slotStatusManager.isLaneSpinning(location, selectedLane)) {
                return;
            }
        } catch (LaneNumberWrongException e){
            stopSlotMachine();
            return;
        }
        switch (selectedLane){
            case 1 -> {
                location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.8f, 0.8f);
                rotateTaskSlot1.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE1_SPINNING);
            }
            case 2 -> {
                location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.8f, 1.0f);
                rotateTaskSlot2.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE2_SPINNING);
            }
            case 3 -> {
                location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.8f, 1.2f);
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
        long rewardAmount = 0L;

        // 結果処理をここに実装
        // とりあえず全部揃ってるか判定
        if(item1 != null && item2 != null && item3 != null &&
           item1.isSimilar(item2) && item2.isSimilar(item3)){
            switch (item1.getType()){
                case Material.PLAYER_HEAD -> {
                    String ownerName = "dummy";
                    if(item1.getItemMeta() instanceof SkullMeta skullMeta){
                        if(skullMeta.getOwningPlayer() != null){
                            ownerName = skullMeta.getOwningPlayer().getName();
                        }else{
                            break;
                        }
                    }
                    if(ownerName == null){
                        break;
                    }
                    if(ownerName.equals("yurisi")){
                        location.getWorld().playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
                        location.getWorld().spawnParticle(Particle.FIREWORK, location, 5);
                        rewardAmount = Roller.YURISI_AWARD;
                    }else if(ownerName.equals("Villagermeyason")){
                        location.getWorld().playSound(location, Sound.ENTITY_VILLAGER_YES, 1.0f, 1.0f);
                        rewardAmount = Roller.MEYASON_AWARD;
                    }
                }
                case Material.DIAMOND -> rewardAmount = Roller.DIAMOND_AWARD;
                case Material.BELL -> rewardAmount = Roller.BELL_AWARD;
                case Material.GLOW_BERRIES -> rewardAmount = Roller.GLOW_BERRIES_AWARD;
                case Material.SWEET_BERRIES -> rewardAmount = Roller.SWEET_BERRIES_AWARD;
                case Material.COD -> rewardAmount = Roller.COD_AWARD;
                case Material.GREEN_BUNDLE -> rewardAmount = Roller.GREEN_BUNDLE_AWARD;
            }
            if(rewardAmount > 0L){
                try{
                    UniverseEconomyAPI.getInstance().addMoney(player, rewardAmount, "スロット当選報酬");
                    Message.sendSuccessMessage(player, "[スロットAI]", "おめでとうございます！" + rewardAmount + "円を獲得しました！");
                } catch (UserNotFoundException | MoneyNotFoundException e){
                    Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報が見つかりません。スロットの報酬を付与できません。");
                } catch (ParameterException e){
                    Message.sendErrorMessage(player, "[スロットAI]", "エラー:ParameterExceptionが発生しました。運営にお問い合わせください。");
                } catch (CanNotAddMoneyException e){
                    Message.sendErrorMessage(player, "[スロットAI]", "エラー:CannotAddMoneyExceptionが発生しました。運営にお問い合わせください。");
                }
            }
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
        playerStatusManager.removeFlag(uuid, PlayerStatusManager.ON_SLOT);
        playerStatusManager.removePlayerSlotCore(uuid);
    }

}
