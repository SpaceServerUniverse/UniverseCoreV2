package space.yurisi.universecorev2.subplugins.universeslot.listener.block;

import org.bukkit.Location;
import org.bukkit.block.Shelf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Slot;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.SlotRepository;
import space.yurisi.universecorev2.exception.LaneNumberWrongException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.SlotNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
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

        // slot編集モードは別処理
        if(main.getPlayerStatusManager().hasFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE)){
            return;
        }

        SlotLocationManager slotLocationManager = UniverseSlot.getInstance().getSlotLocationManager();

        Location location = playerInteractEvent.getClickedBlock().getLocation();
        SlotRepository slotRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SlotRepository.class);

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
            try {
                if (slotStatusManager.isLaneSpinning(location, 1)) {
                    slotCore.stopSlot(1);
                    return;
                } else if (slotStatusManager.isLaneSpinning(location, 2)) {
                    slotCore.stopSlot(2);
                    return;
                } else if (slotStatusManager.isLaneSpinning(location, 3)) {
                    slotCore.stopSlot(3);
                    return;
                }
            } catch (LaneNumberWrongException e){
                Message.sendErrorMessage(player, "[スロットAI]", "レーンの状態の取得に失敗しました。管理者にお問い合わせください。");
                return;
            }
            return;
        }

        if(slotStatusManager.isInUse(location)){
            Message.sendErrorMessage(player, "[スロットAI]", "このスロットは他のプレイヤーによって使用中です。");
            return;
        }

        SlotCore slotCore;
        try {
            slotCore = new SlotCore(player, shelf);
        } catch (SlotNotFoundException e) {
            Message.sendErrorMessage(player, "[スロットAI]", "スロットの情報の取得に失敗しました。管理者にお問い合わせください。");
            return;
        }

        if(!slotCore.prepareSlot()){
            return;
        }
        slotCore.startSlot();
        playerStatusManager.setPlayerSlotCore(player.getUniqueId(), slotCore);

    }
}
