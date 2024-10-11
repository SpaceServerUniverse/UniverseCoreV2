package space.yurisi.universecorev2.subplugins.universeguns.connector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.Ammo;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.AmmoRepository;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.AmmoNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeguns.constants.GunType;
import space.yurisi.universecorev2.utils.Message;


public class UniverseCoreAPIConnector {

    private UserRepository userRepository;

    private AmmoRepository ammoRepository;

    public UniverseCoreAPIConnector(DatabaseManager databaseManager){
        setUserRepository(databaseManager.getUserRepository());
        setAmmoRepository(databaseManager.getAmmoRepository());
    }

    public void AmmoDataInit(Player player) throws UserNotFoundException {
        try{
            ammoRepository.getAmmo(userRepository.getPrimaryKeyFromUUID(player.getUniqueId()));
        } catch (AmmoNotFoundException e){
            User user = userRepository.getUserFromUUID(player.getUniqueId());
            ammoRepository.createAmmo(user);
        }
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
