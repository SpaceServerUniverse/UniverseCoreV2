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

        SlotLocationManager slotLocationManager = UniverseSlot.getInstance().getSlotLocationManager();
        PlayerStatusManager playerStatusManager = UniverseSlot.getInstance().getPlayerStatusManager();

        Location location = event.getClickedBlock().getLocation();
        SlotRepository slotRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(SlotRepository.class);

        if(main.getPlayerStatusManager().hasFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE)){
            if (event.getHand() != EquipmentSlot.HAND) return;
            event.setCancelled(true);

            LandDataManager landDataManager = LandDataManager.getInstance();
            if(!landDataManager.canAccess(player, new BoundingBox(shelf.getX(), shelf.getZ(), shelf.getX(), shelf.getZ(), shelf.getWorld().getName()))){
                Message.sendErrorMessage(player, "[スロットAI]", "あなたの土地ではないため編集できません。");
                return;
            }

            Slot slot;

            playerStatusManager.setClickedLocation(player.getUniqueId(), event.getClickedBlock().getLocation());
            Message.sendNormalMessage(player, "[スロットAI]", "対象：(" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ") の棚を編集します。");

            main.getPlayerStatusManager().removeFlag(player.getUniqueId(), PlayerStatusManager.ON_EDIT_MODE);

            try{
                // ここで例外吐いたらスロットではない
                slot = slotRepository.getSlotFromCoordinates((long)location.getX(), (long)location.getY(), (long)location.getZ(), location.getWorld().getName());

                // 以降スロット解除処理
                if(player.isOp() || player.getUniqueId().equals(UUID.fromString(slot.getUuid()))){
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
                try{
                    UniverseEconomyAPI.getInstance().reduceMoney(player, 10000L, "スロット設置費用");
                } catch (UserNotFoundException | MoneyNotFoundException exception){
                    Message.sendErrorMessage(player, "[スロットAI]", "ユーザーかお金の情報の取得に失敗しました。管理者にお問い合わせください。");
                    return;
                } catch (CanNotReduceMoneyException exception){
                    Message.sendErrorMessage(player, "[スロットAI]", "お金が不足しているためスロットを設置できません。");
                    return;
                } catch (ParameterException exception){
                    Message.sendErrorMessage(player, "[スロットAI]", "パラメーターの値が不正です。管理者にお問い合わせください。");
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
    }
}
