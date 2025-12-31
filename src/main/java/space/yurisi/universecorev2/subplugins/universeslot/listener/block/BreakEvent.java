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
import space.yurisi.universecorev2.exception.SlotNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
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

        // スロット所有者なら解除可能
        Slot slot;
        try{
            slot = slotRepository.getSlotFromCoordinates((long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());

            if(player.getUniqueId().equals(UUID.fromString(slot.getUuid()))){
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

        } catch (SlotNotFoundException | UserNotFoundException e) {
            return;
        }
    }
}
