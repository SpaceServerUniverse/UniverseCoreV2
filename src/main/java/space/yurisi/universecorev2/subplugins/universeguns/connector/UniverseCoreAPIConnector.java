package space.yurisi.universecorev2.subplugins.universeguns.connector;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.repositories.AmmoRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;


public class UniverseCoreAPIConnector {

    private UserRepository userRepository;

    private AmmoRepository ammoRepository;

    public UniverseCoreAPIConnector(DatabaseManager databaseManager){
        setUserRepository(databaseManager.getUserRepository());
        setAmmoRepository(databaseManager.getAmmoRepository());
    }

    public boolean isExistsAmmoData(Player player) {
        try {
            Long ammo = ammoRepository.getAmmoFromUserId(userRepository.getPrimaryKeyFromUUID(player.getUniqueId()), GunType.HG);
            return ammo != null;
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (AmmoNotFoundException e) {
            return false;
        }
    }

    public void AmmoDataInit(Player player) throws UserNotFoundException {

        ammoRepository.createAmmoFromUUID(userRepository.getPrimaryKeyFromUUID(player.getUniqueId()));

    }

    public Long getAmmoFromUserId(Player player, GunType gunType) throws UserNotFoundException, AmmoNotFoundException {
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        return ammoRepository.getAmmoFromUserId(user_id, gunType);
    }

    public void setAmmoFromUserId(Player player, GunType gunType, Long ammo) throws UserNotFoundException, AmmoNotFoundException {
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        ammoRepository.updateAmmo(user_id, ammo, gunType);
    }

    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void setAmmoRepository(AmmoRepository ammoRepository) {
        this.ammoRepository = ammoRepository;
    }
}
