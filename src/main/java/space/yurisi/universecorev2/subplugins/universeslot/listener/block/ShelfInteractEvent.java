package space.yurisi.universecorev2.subplugins.universeslot.listener.block;

import org.bukkit.Location;
import org.bukkit.block.Shelf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Slot;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.SlotRepository;
import space.yurisi.universecorev2.exception.SlotNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeland.manager.LandDataManager;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.core.SlotCore;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotLocationManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotStatusManager;
import space.yurisi.universecorev2.utils.Message;

import java.util.UUID;

public class ShelfInteractEvent implements Listener {

    private final UniverseSlot main;

    public ShelfInteractEvent(UniverseSlot main){
        this.main = main;
    }

    @EventHandler
    public void onInteractShelf(PlayerInteractEvent playerInteractEvent) {
        Player player = playerInteractEvent.getPlayer();

        if(playerInteractEvent.getClickedBlock() == null) {
            return;
        }

        if(!(playerInteractEvent.getClickedBlock().getState() instanceof Shelf shelf)) {
            return;
        }

        SlotLocationManager slotLocationManager = UniverseSlot.getInstance().getSlotManager();

        Location location = playerInteractEvent.getClickedBlock().getLocation();
        SlotRepository slotRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SlotRepository.class);

        // slot編集モード
        if(main.getPlayerStatusManager().hasFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE)){
            if (playerInteractEvent.getHand() != EquipmentSlot.HAND) return;
            playerInteractEvent.setCancelled(true);

            LandDataManager landDataManager = LandDataManager.getInstance();
            if(!landDataManager.canAccess(player, new BoundingBox(shelf.getX(), shelf.getZ(), shelf.getX(), shelf.getZ(), shelf.getWorld().getName()))){
                Message.sendErrorMessage(player, "[スロットAI]", "あなたの土地ではないため編集できません。");
                return;
            }

            Slot slot;

            main.getPlayerStatusManager().removeFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE);

            try{
                // ここで例外吐いたらスロットではない
                slot = slotRepository.getSlotFromCoordinates((long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());

                // 以降スロット解除処理
                if(player.getUniqueId().equals(UUID.fromString(slot.getUuid()))){
                    SlotCore slotCore = main.getPlayerStatusManager().getPlayerSlotCore(player.getUniqueId());
                    if(slotCore != null){
                        slotCore.stopSlotMachine();
                        main.getPlayerStatusManager().removePlayerSlotCore(player.getUniqueId());
                    }
                    slotLocationManager.unregisterSlotLocation(location);
                    slotRepository.deleteSlot(slot);
                    shelf.getInventory().clear();
                    Message.sendSuccessMessage(player, "[スロットAI]", "スロットの登録を解除しました。");
                    return;
                }
                User user = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUserFromUUID(UUID.fromString(slot.getUuid()));
                Message.sendNormalMessage(player, "[スロットAI]", "他のユーザーによってこのスロットは登録されています。持ち主：" + user.getName());

            } catch (SlotNotFoundException e) {
                // 以降スロット登録
                if(!shelf.getInventory().isEmpty()){
                    Message.sendErrorMessage(player, "[スロットAI]", "棚が空ではないためスロットにできません。");
                    return;
                }
                slotRepository.createSlot(player.getUniqueId(), (long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());
                slotLocationManager.registerSlotLocation(location, player.getUniqueId());
                Message.sendSuccessMessage(player, "[スロットAI]", "スロットを作成しました");

                shelf.getInventory().setItem(0, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
                shelf.getInventory().setItem(1, UniverseSlot.getInstance().getRoller().getRandomRotateItem());
                shelf.getInventory().setItem(2, UniverseSlot.getInstance().getRoller().getRandomRotateItem());

            } catch (UserNotFoundException e) {
                return;
            }
            return;
        }
        if(!playerInteractEvent.getAction().isRightClick()){
            return;
        }

        // スロットじゃないのはここで弾かれる(一回dbから探して以降メモリ)
        if(!slotLocationManager.isSlotLocation(location)){
            Slot slot;
            try{
                slot = slotRepository.getSlotFromCoordinates((long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());
                slotLocationManager.registerSlotLocation(location, UUID.fromString(slot.getUuid()));
            } catch (SlotNotFoundException e) {
                return;
            }
        }

        playerInteractEvent.setCancelled(true);

        PlayerStatusManager playerStatusManager = UniverseSlot.getInstance().getPlayerStatusManager();
        SlotStatusManager slotStatusManager = UniverseSlot.getInstance().getSlotStatusManager();

        // スロット既に開始済みの処理(レーン止める)
        if(playerStatusManager.hasPlayerSlotCore(player.getUniqueId())){
            if(!playerStatusManager.getPlayerSlotCore(player.getUniqueId()).getLocation().equals(location)){
                Message.sendErrorMessage(player, "[スロットAI]", "あなたはこのスロットを操作していません。");
                return;
            }

            SlotCore slotCore = playerStatusManager.getPlayerSlotCore(player.getUniqueId());
            if(slotStatusManager.isLaneSpinning(location, 1)){
                slotCore.stopSlot(1);
                return;
            }else if (slotStatusManager.isLaneSpinning(location, 2)){
                slotCore.stopSlot(2);
                return;
            } else if (slotStatusManager.isLaneSpinning(location, 3)){
                slotCore.stopSlot(3);
                return;
            }
            return;
        }

        if(slotStatusManager.isInUse(location)){
            Message.sendErrorMessage(player, "[スロットAI]", "このスロットは他のプレイヤーによって使用中です。");
            return;
        }

        SlotCore slotCore = new SlotCore(player, shelf);
        if(!slotCore.startSlot()){
            Message.sendErrorMessage(player, "[スロットAI]", "スロットの開始に失敗しました。");
            return;
        }
        playerStatusManager.setPlayerSlotCore(player.getUniqueId(), slotCore);

    }
}
