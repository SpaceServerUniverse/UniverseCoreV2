package space.yurisi.universecorev2.subplugins.containerprotect.manager;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.ContainerProtect;
import space.yurisi.universecorev2.database.repositories.ContainerProtectRepository;
import space.yurisi.universecorev2.subplugins.containerprotect.util.DoubleChestFinder;

import java.util.HashMap;
import java.util.Objects;

public class ContainerProtectManager {

    private static ContainerProtectManager instance;

    private final ContainerProtectRepository containerProtectRepository = UniverseCoreV2API.getInstance().getDatabaseManager().getContainerProtectRepository();
    private final HashMap<Location, ContainerProtect> containerProtectCache = new HashMap<>();

    public ContainerProtectManager() {
        instance = this;
    }

    public static ContainerProtectManager getInstance() {
        return instance;
    }

    public boolean isContainerProtect(Location location) {
        Block block = location.getBlock();
        InventoryHolder holder = (InventoryHolder) block.getState();

        if(getContainerProtect(location) != null) return true;

        if(holder instanceof Chest chest) {
            BlockFace face = DoubleChestFinder.getNeighborBlockFace((org.bukkit.block.data.type.Chest) chest.getBlockData());
            if(face != null){
                Block neighborBlock = block.getRelative(face);
                if(neighborBlock.getState() instanceof Chest) return getContainerProtect(neighborBlock.getLocation()) != null;
            }
        }

        return false;
    }

    public boolean canAccessContainer(Player player, Location location) {
        Block block = location.getBlock();
        InventoryHolder holder = (InventoryHolder) block.getState();

        if(!(holder instanceof Chest chest)) return true;

        ContainerProtect containerProtect = getContainerProtect(location);
        if(containerProtect == null || Objects.equals(containerProtect.getUuid(), player.getUniqueId().toString())) return true;

        BlockFace face = DoubleChestFinder.getNeighborBlockFace((org.bukkit.block.data.type.Chest) chest.getBlockData());
        if(face != null){
            Block neighborBlock = block.getRelative(face);
            if(neighborBlock.getState() instanceof Chest) {
                ContainerProtect anotherContainerProtect = getContainerProtect(neighborBlock.getLocation());
                return anotherContainerProtect == null || Objects.equals(anotherContainerProtect.getUuid(), player.getUniqueId().toString());
            }
        }

        return true;
    }

    public void addContainerProtect(Player player, Location location) {
        ContainerProtect containerProtect = containerProtectRepository.createContainerProtect(player, location);
        containerProtectCache.put(location, containerProtect);
    }

    public void removeContainerProtect(Location location) {
        ContainerProtect containerProtect = null;

        if (containerProtectCache.containsKey(location)) containerProtect = containerProtectCache.get(location);
        if (containerProtectRepository.existsContainerProtectFromLocation(location))
            containerProtect = containerProtectRepository.getContainerProtectFromLocation(location);
        if (containerProtect != null) containerProtectRepository.deleteContainerProtect(containerProtect);
    }

    public ContainerProtect getContainerProtect(Location location) {
        if (containerProtectCache.containsKey(location)) return containerProtectCache.get(location);
        if (containerProtectRepository.existsContainerProtectFromLocation(location)){
            ContainerProtect containerProtect = containerProtectRepository.getContainerProtectFromLocation(location);
            containerProtectCache.put(location, containerProtect);
            return containerProtect;
        }

        Block block = location.getBlock();
        InventoryHolder holder = (InventoryHolder) block.getState();

        if(holder instanceof Chest chest) {
            BlockFace face = DoubleChestFinder.getNeighborBlockFace((org.bukkit.block.data.type.Chest) chest.getBlockData());
            if(face != null){
                Block neighborBlock = block.getRelative(face);
                if(neighborBlock.getState() instanceof Chest){
                    ContainerProtect containerProtect = containerProtectRepository.getContainerProtectFromLocation(neighborBlock.getLocation());
                    containerProtectCache.put(neighborBlock.getLocation(), containerProtect);
                    return containerProtect;
                }
            }
        }
        return null;
    }
}
