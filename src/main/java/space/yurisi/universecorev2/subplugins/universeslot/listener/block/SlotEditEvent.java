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
import space.yurisi.universecorev2.utils.Message;

import java.util.UUID;

public class SlotEditEvent implements Listener {

    private UniverseSlot main;

    public SlotEditEvent(UniverseSlot main){
        this.main = main;
    }

    @EventHandler
    public void onInteractShelf(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getClickedBlock() == null) {
            return;
        }

        if(!(event.getClickedBlock().getState() instanceof Shelf shelf)) {
            return;
        }

        PlayerStatusManager playerStatusManager = UniverseSlot.getInstance().getPlayerStatusManager();

        Location location = event.getClickedBlock().getLocation();

        if(main.getPlayerStatusManager().hasFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE)){
            if (event.getHand() != EquipmentSlot.HAND) return;
            event.setCancelled(true);

            LandDataManager landDataManager = LandDataManager.getInstance();
            if(!landDataManager.canAccess(player, new BoundingBox(shelf.getX(), shelf.getZ(), shelf.getX(), shelf.getZ(), shelf.getWorld().getName()))){
                Message.sendErrorMessage(player, "[スロットAI]", "あなたの土地ではないため編集できません。");
                return;
            }

            playerStatusManager.setClickedLocation(player.getUniqueId(), event.getClickedBlock().getLocation());
            Message.sendNormalMessage(player, "[スロットAI]", "対象：(" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ") の棚を編集します。");

        }
    }
}
