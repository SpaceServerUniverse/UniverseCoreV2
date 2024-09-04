package space.yurisi.universecorev2.subplugins.universeland.event.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import space.yurisi.universecorev2.exception.LandNotFoundException;
import space.yurisi.universecorev2.subplugins.universeland.UniverseLand;
import space.yurisi.universecorev2.subplugins.universeland.manager.LandDataManager;
import space.yurisi.universecorev2.subplugins.universeland.store.LandData;
import space.yurisi.universecorev2.subplugins.universeland.store.LandStore;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;
import space.yurisi.universecorev2.subplugins.universeland.utils.Vector2;

import java.util.UUID;

public class TouchEvent implements Listener {

    private final LandDataManager landDataManager = new LandDataManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTouch(PlayerInteractEvent event) throws LandNotFoundException {
        if (event.getHand() != EquipmentSlot.HAND) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Block block = event.getClickedBlock();

        if (block == null) return;

        Material type = block.getType();

        Block relativeBlock = block.getRelative(event.getBlockFace());

        LandDataManager landDataManager = LandDataManager.getInstance();
        BoundingBox bb = new BoundingBox(block.getX(), block.getZ(), block.getX(), block.getZ(), block.getWorld().getName());

        if (!landDataManager.canAccess(player, bb) && type.isInteractable()) {
            event.setCancelled(true);

            LandData data = landDataManager.getLandData(bb);

            OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(data.getOwnerUUID());
            player.sendActionBar(Component.text("この土地は" + p.getName() + "によって保護されています"));
            return;
        }

        if (landDataManager.getLandData(uuid) == null) {
            initLandData(player);
        }

        LandStore landData = landDataManager.getLandData(uuid);

        if (landData.isSelectLand()) {
            event.setCancelled(true);

            int x = block.getX();
            int z = block.getZ();

            if (landData.getStartPosition() == null) {
                landData.setStartPosition(new Vector2(x, z));
                landData.setWorldName(player.getWorld().getWorldFolder().getName());
                player.sendMessage(Component.text("StartPositionを設定しました (X: " + x + ", Z: " + z + ")"));
            } else {
                landData.setEndPosition(new Vector2(x, z));
                landData.setSelectLand(false);

                int size = landData.getLand().getSize();

                if (size <= 1) {
                    player.sendMessage(Component.text("保護する範囲は、2マス以上にしてください"));
                    landData.resetLandData();
                    return;
                } else if (!player.getWorld().getWorldFolder().getName().equals(landData.getWorldName())) {
                    player.sendMessage(Component.text("同じワールドで範囲を指定してください"));
                    landData.resetLandData();
                    return;
                }

                LandData overlapLandData = LandDataManager.getInstance().getLandData(landData.getLand());

                if (overlapLandData != null) {
                    OfflinePlayer p = Bukkit.getServer().getOfflinePlayer(overlapLandData.getOwnerUUID());
                    player.sendMessage(Component.text("選択した範囲は、" + p.getName() + "によって保護されています"));
                    return;
                }

                player.sendMessage(Component.text("EndPositionを設定しました (X: " + x + ", Z: " + z + ")"));
                player.sendMessage(Component.text("サイズ: " + size + "ブロック (値段: " + landData.getPrice() + "star)"));
                if (player.getName().startsWith("*")) {
                    player.sendMessage(Component.text("指定した範囲の土地を購入する際は、/land buyを実行してください"));
                } else {
                    Component component = Component.text("[ここをクリックで土地を購入]")
                            .clickEvent(ClickEvent.runCommand("/land buy"))
                            .hoverEvent(HoverEvent.showText(Component.text("クリックすると土地を購入します")));
                    player.sendMessage(component);
                }
            }
        }
    }

    private void initLandData(Player player) {
        landDataManager.setLandData(player.getUniqueId());
    }
}
