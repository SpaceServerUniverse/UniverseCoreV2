package space.yurisi.universecorev2.subplugins.universeland.store;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.universeland.utils.BoundingBox;

import java.util.List;
import java.util.UUID;

public class LandData {

    private final Long id;

    private final UUID ownerUUID;
    private final Long price;
    private final BoundingBox boundingBox;
    private final List<UUID> allowedList;

    public LandData(Long id, UUID ownerUUID, Long price, BoundingBox boundingBox, List<UUID> allowedList) {
        this.id = id;
        this.ownerUUID = ownerUUID;
        this.price = price;
        this.boundingBox = boundingBox;
        this.allowedList = allowedList;
    }

    public Long getId() {
        return id;
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    public Long getPrice() {
        return price;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public List<UUID> getAllowedList() {
        return allowedList;
    }

    public boolean isOwner(Player player) {
        return ownerUUID.toString().equals(player.getUniqueId().toString());
    }

    public boolean canAccess(Player player) {
        if (player.isOp() || ownerUUID.toString().equals(player.getUniqueId().toString())) {
            return true;
        }

        for (UUID uuid : allowedList) {
            if (uuid.toString().equals(player.getUniqueId().toString())) {
                return true;
            }
        }

        return false;
    }
}
