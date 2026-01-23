package space.yurisi.universecorev2.subplugins.universeslot.listener.block;

import org.bukkit.Location;
import org.bukkit.block.Shelf;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Slot;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.SlotRepository;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.SlotNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeslot.UniverseSlot;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotLocationManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotStatusManager;
import space.yurisi.universecorev2.utils.Message;

import java.util.UUID;

public class BreakEvent implements Listener {

    @EventHandler
    public void onBreakShelf(BlockBreakEvent event){
        Player player = event.getPlayer();

        if(!(event.getBlock().getState() instanceof Shelf shelf)) {
            return;
        }

        SlotLocationManager slotLocationManager = UniverseSlot.getInstance().getSlotLocationManager();
        Location location = event.getBlock().getLocation();
        SlotRepository slotRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SlotRepository.class);

        SlotStatusManager slotStatusManager = UniverseSlot.getInstance().getSlotStatusManager();
        // 使用中は破壊不可
        if(slotStatusManager.isInUse(location)){
            Message.sendErrorMessage(player, "[スロットAI]", "このスロットは使用中です。");
            event.setCancelled(true);
            return;
        }

        // スロット所有者なら解除可能(OPの場合は/slot unregisterでのみ可能)
        Slot slot = null;
        try{
            slot = slotRepository.getSlotFromCoordinates((long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());

            if(player.getUniqueId().equals(UUID.fromString(slot.getUuid()))){
                Long remainCash = slot.getCash();
                if (remainCash > 0) {
                    UniverseEconomyAPI.getInstance().addMoney(player, remainCash, "スロット解除による残高返還");
                    Message.sendNormalMessage(player, "[スロットAI]", "スロット内に残っていた" + remainCash + "円を返還しました。");
                }
                slotRepository.deleteSlot(slot);
                if(slotLocationManager.isSlotLocation(location)){
                    slotLocationManager.unregisterSlotLocation(location);
                }
                shelf.getInventory().clear();
                Message.sendSuccessMessage(player, "[スロットAI]", "スロットの登録を解除しました。");

                return;
            }

            User user = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUserFromUUID(UUID.fromString(slot.getUuid()));
            Message.sendNormalMessage(player, "[スロットAI]", "他のユーザーによってこのスロットは登録されています。持ち主：" + user.getName());
            event.setCancelled(true);

        } catch (SlotNotFoundException | UserNotFoundException | MoneyNotFoundException e) {
            return;
        }  catch (CanNotAddMoneyException e) {
            Message.sendErrorMessage(player, "[スロットAI]", "スロット内の残高を返還できませんでした。管理者にお問い合わせください。");
            Message.sendErrorMessage(player, "[スロットAI]", "残高: " + slot.getCash() + "円");
            event.setCancelled(true);
        } catch (ParameterException e) {
            Message.sendErrorMessage(player, "[スロットAI]", "パラメーターの値が不正です。管理者にお問い合わせください。");
            event.setCancelled(true);
        }
    }
}
