package space.yurisi.universecorev2.subplugins.universeslot.core;

import org.bukkit.*;
import org.bukkit.block.Shelf;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Slot;
import space.yurisi.universecorev2.database.repositories.SlotRepository;
import space.yurisi.universecorev2.exception.*;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.RoleManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotStatusManager;
import space.yurisi.universecorev2.utils.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SlotCore {

    private final Player player;
    private final UUID uuid;
    private final UUID ownerUUID;

    private final Shelf shelf;

    private List<Integer> currentIndexSlots;

    private List<List<ItemStack>> rotateItemLanes;

    private BukkitRunnable rotateTaskSlot1;
    private BukkitRunnable rotateTaskSlot2;
    private BukkitRunnable rotateTaskSlot3;

    private boolean isMissed = false;

    private final PlayerStatusManager playerStatusManager;
    private final SlotStatusManager slotStatusManager;

    private final SlotRepository slotRepository;
    private final Slot slot;

    private ItemStack roleItem;

    private boolean onFreeze = false;

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
        try{
            slotRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SlotRepository.class);
            slot = slotRepository.getSlotFromCoordinates((long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());
        } catch (SlotNotFoundException e) {
            throw new SlotNotFoundException("Slot not found at location: " + location);
        }
        this.currentIndexSlots = new ArrayList<>(List.of(0, 0, 0));
        this.rotateItemLanes = List.of(List.of());
    }

    public boolean prepareSlot(){
        if(playerStatusManager.hasFlag(uuid, PlayerStatusManager.ON_SLOT)){
            Message.sendErrorMessage(player, "[スロットAI]", "既に他のスロットを利用中です。");
            return false;
        }
        if(slotStatusManager.isInUse(location)){
            Message.sendErrorMessage(player, "[スロットAI]", "他のプレイヤーが利用中です。");
            return false;
        }
        if(player.getVehicle() == null){
            Message.sendErrorMessage(player, "[スロットAI]", "椅子に座ってスロットを引いてください。");
            return false;
        }
        if(slot.getCash() < 5000L){
            Message.sendErrorMessage(player, "[スロットAI]", "このスロットはメンテナンス中のため利用できません。");
            Player owner = Bukkit.getPlayer(ownerUUID);
            if(owner != null && owner.isOnline()){
                Message.sendWarningMessage(owner, "[スロットAI]", "あなたのスロットの残高が不足しています！(" + shelf.getX() + ", " + shelf.getY() + ", " + shelf.getZ() + ")");
            }
            return false;
        }

        try{
            UniverseEconomyAPI.getInstance().reduceMoney(player, 10L, "スロット利用料");
            slotRepository.updateCash(slot, 10L);
        } catch (UserNotFoundException | MoneyNotFoundException e){
            Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報が見つかりません。スロットを利用できません。");
            return false;
        } catch (CanNotReduceMoneyException e){
            Message.sendErrorMessage(player, "[スロットAI]", "お金が足りないためスロットを利用できません。");
            return false;
        } catch (ParameterException e){
            Message.sendErrorMessage(player, "[スロットAI]", "エラー:ParameterExceptionが発生しました。運営にお問い合わせください。");
            return false;
        } catch (CannotReduceSlotCashException e){
            Message.sendErrorMessage(player, "[スロットAI]", "スロットの残高を減らせません。運営にお問い合わせください。");
            return false;
        }

        return true;
    }

    public void startSlot(){
        slotStatusManager.addFlag(location, SlotStatusManager.IN_USE);
        playerStatusManager.addFlag(uuid, PlayerStatusManager.ON_SLOT);

        // 1/8192でフリーズに突入
        Random random = new Random();
        int freezeChance = random.nextInt(8192);
        if(freezeChance == 0){
            onFreeze = true;
            playerStatusManager.addFlag(uuid, PlayerStatusManager.ON_FREEZE_MODE);
            List<ItemStack> freezeLane = UniverseSlot.getInstance().getRoller().createFreezeLane(15);
            rotateItemLanes = List.of(freezeLane, freezeLane, freezeLane);
            currentIndexSlots = new ArrayList<>(List.of(0, 0, 0));
            shelf.getInventory().setItem(0, rotateItemLanes.get(0).getFirst());
            shelf.getInventory().setItem(1, rotateItemLanes.get(1).getFirst());
            shelf.getInventory().setItem(2, rotateItemLanes.get(2).getFirst());

            player.sendTitle("§c§lFREEZE!", "");

            player.getWorld().playSound(location, Sound.AMBIENT_CAVE, 8.0f, 1.0f);

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 2, false, false));

        }else {
            int config = slot.getConfig();
            RoleManager manager = UniverseSlot.getInstance().getRoleManager();
            RoleManager.SlotRole role = manager.drawRole(config);
            roleItem = UniverseSlot.getInstance().getRoller().getItemFromRole(role);

            // ロール作成
            rotateItemLanes = UniverseSlot.getInstance().getRoller().createRandomLane(20);

            for(int i = 0; i < 3; i++){
                currentIndexSlots.set(i, rotateItemLanes.get(i).indexOf(shelf.getInventory().getItem(i)));
            }
        }

        rotateTaskSlot1 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlots.set(0, (currentIndexSlots.get(0) + 1) % rotateItemLanes.getFirst().size());
                shelf.getInventory().setItem(0, rotateItemLanes.get(0).get(currentIndexSlots.get(0)));
            }
        };
        rotateTaskSlot2 = new BukkitRunnable() {
            @Override
            public void run() {
                currentIndexSlots.set(1, (currentIndexSlots.get(1) + 1) % rotateItemLanes.get(1).size());
                shelf.getInventory().setItem(1, rotateItemLanes.get(1).get(currentIndexSlots.get(1)));
            }
        };
        rotateTaskSlot3 = new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getVehicle() == null) {
                    Message.sendErrorMessage(player, "[スロットAI]", "椅子から降りたためスロットを強制終了します。");
                    stopSlotMachine();
                    return;
                }
                currentIndexSlots.set(2, (currentIndexSlots.get(2) + 1) % rotateItemLanes.get(2).size());
                shelf.getInventory().setItem(2, rotateItemLanes.get(2).get(currentIndexSlots.get(2)));
            }
        };

        rotateTaskSlot1.runTaskTimer(UniverseCoreV2.getInstance(), 0L, Roller.ROTATE_INTERVAL);
        rotateTaskSlot2.runTaskTimer(UniverseCoreV2.getInstance(), 2L, Roller.ROTATE_INTERVAL);
        rotateTaskSlot3.runTaskTimer(UniverseCoreV2.getInstance(), 4L, Roller.ROTATE_INTERVAL);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE1_SPINNING);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE2_SPINNING);
        slotStatusManager.addFlag(location, SlotStatusManager.LANE3_SPINNING);

        location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_HARP, 0.8f, 1.0f);
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
                if(canAssist(selectedLane)){
                    shelf.getInventory().setItem(0, roleItem);
                }else{
                    isMissed = true;
                }
            }
            case 2 -> {
                location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.8f, 1.0f);
                rotateTaskSlot2.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE2_SPINNING);
                if(canAssist(selectedLane) && !isMissed){
                    shelf.getInventory().setItem(1, roleItem);
                }else{
                    isMissed = true;
                }
            }
            case 3 -> {
                location.getWorld().playSound(location, Sound.BLOCK_NOTE_BLOCK_BELL, 0.8f, 1.2f);
                rotateTaskSlot3.cancel();
                slotStatusManager.removeFlag(location, SlotStatusManager.LANE3_SPINNING);
                if(canAssist(selectedLane) && !isMissed){
                    shelf.getInventory().setItem(2, roleItem);
                }else{
                    if(isShouldFumble()){
                        currentIndexSlots.set(2, (currentIndexSlots.get(2) + 1) % rotateItemLanes.get(2).size());
                        shelf.getInventory().setItem(2, rotateItemLanes.get(2).get(currentIndexSlots.get(2)));
                    }
                }
            }
        }
        if(slotStatusManager.getSpinningLaneCount(location) == 0){
            resultSlot();
        }
    }

    public boolean isShouldFumble(){
        if(onFreeze){
            return false;
        }
        ItemStack item1 = rotateItemLanes.get(0).get(currentIndexSlots.get(0));
        ItemStack item2 = rotateItemLanes.get(1).get(currentIndexSlots.get(1));
        ItemStack item3 = rotateItemLanes.get(2).get(currentIndexSlots.get(2));
        if(item1 != null && item2 != null && item3 != null &&
                item1.isSimilar(item2) && item2.isSimilar(item3)){
            return !item3.isSimilar(roleItem);
        }else{
            return false;
        }
    }

    public boolean canAssist(int laneNumber){
        if(onFreeze){
            return false;
        }
        // 後3つのアイテムを確認して、roleItemと一致するか確認
        List<ItemStack> laneItems = rotateItemLanes.get(laneNumber - 1);
        int currentIndex = currentIndexSlots.get(laneNumber - 1);
        for(int offset = 0; offset <= 4; offset++){
            int index = (currentIndex + offset + laneItems.size()) % laneItems.size();
            ItemStack item = laneItems.get(index);
            if(item != null && item.isSimilar(roleItem)){
                return true;
            }
        }
        return false;
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
                case Material.DRAGON_HEAD ->{
                    rewardAmount = Roller.ENDER_DRAGON_HEAD_AWARD;
                    location.getWorld().playSound(location, Sound.ENTITY_ENDER_DRAGON_DEATH, 3.0f, 1.0f);
                    Float dragonBreath = 1.0f;
                    location.getWorld().spawnParticle(Particle.DRAGON_BREATH, location, 100, 3.0, 1.0, 3.0, 0.1, dragonBreath);
                }
            }
            if(rewardAmount > 0L){
                try {
                    slotRepository.updateCash(slot, -rewardAmount);
                    UniverseEconomyAPI.getInstance().addMoney(player, rewardAmount, "スロット当選報酬");
                    Message.sendSuccessMessage(player, "[スロットAI]", "おめでとうございます！" + rewardAmount + "円を獲得しました！");
                } catch (CannotReduceSlotCashException e){
                    Message.sendErrorMessage(player, "[スロットAI]", "スロットの残高を減らせません。運営にお問い合わせください。");
                    Message.sendErrorMessage(player, "[スロットAI]", "報酬金額: " + rewardAmount + "円");
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

        if(onFreeze){
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            playerStatusManager.removeFlag(uuid, PlayerStatusManager.ON_FREEZE_MODE);
            shelf.getInventory().setItem(0, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
            shelf.getInventory().setItem(1, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
            shelf.getInventory().setItem(2, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
        }
    }

}
