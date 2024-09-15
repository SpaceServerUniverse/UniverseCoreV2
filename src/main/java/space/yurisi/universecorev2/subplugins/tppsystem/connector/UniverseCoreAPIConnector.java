package space.yurisi.universecorev2.subplugins.tppsystem.connector;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.AutoTppSetting;
import space.yurisi.universecorev2.database.repositories.AutoTppSettingRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class UniverseCoreAPIConnector {

    private final UserRepository userRepository;

    private AutoTppSettingRepository autoTPPSettingRepository;


    public UniverseCoreAPIConnector(DatabaseManager databaseManager){
        this.userRepository = databaseManager.getUserRepository();
        this.autoTPPSettingRepository = databaseManager.getAutoTPPSettingRepository();
    }

    public Boolean isExistsAutoTPPSetting(Player player){
        try {
            Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
            AutoTppSetting data = autoTPPSettingRepository.getAutoTPPSetting(user_id);
            return data != null;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    public AutoTppSetting getAutoTPPSettingFromPlayer(Player player) throws UserNotFoundException{
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        return autoTPPSettingRepository.getAutoTPPSetting(user_id);
    }


    public Boolean isAutoAccept(Player player) throws UserNotFoundException {
        return autoTPPSettingRepository.isAutoAccept(player);
    }

    public void createAutoTPPSetting(Player player) throws UserNotFoundException {
        autoTPPSettingRepository.createAutoTppSetting(player);
    }

    public void changeAutoTPPSetting(AutoTppSetting autoTppSetting, Boolean is_auto_accept) throws UserNotFoundException {
        autoTppSetting.setIs_auto_accept(is_auto_accept);
        autoTPPSettingRepository.updateAutoTPPSetting(autoTppSetting);
    }
}
