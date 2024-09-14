package space.yurisi.universecorev2.subplugins.tppsystem.connector;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.AutoTppSetting;
import space.yurisi.universecorev2.database.repositories.AutoTppSettingRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class UniverseCoreAPIConnector {

    private UserRepository userRepository;

    private AutoTppSettingRepository autoTPPSettingRepository;


    public UniverseCoreAPIConnector(DatabaseManager databaseManager){
        setUserRepository(databaseManager.getUserRepository());
        setAutoTPPSettingRepository(databaseManager.getAutoTPPSettingRepository());
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


    public void updateAutoTPPSetting(AutoTppSetting autoTPPSetting){
        autoTPPSettingRepository.updateAutoTPPSetting(autoTPPSetting);
    }

    public void createAutoTPPSetting(Player player){
        autoTPPSettingRepository.createAutoTppSetting(player);
    }

    public void deleteAutoTPPSetting(AutoTppSetting autoTPPSetting){
        autoTPPSettingRepository.deleteAutoTPPSetting(autoTPPSetting);
    }

    public Boolean getAutoTPPSetting(Player player) throws UserNotFoundException {
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        AutoTppSetting autoTPPSetting = autoTPPSettingRepository.getAutoTPPSetting(user_id);
        return autoTPPSetting.getIs_auto_accept();
    }

    public void setAutoTPPSetting(Player player, Boolean is_auto_accept) throws UserNotFoundException {
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        AutoTppSetting autoTPPSetting = autoTPPSettingRepository.getAutoTPPSetting(user_id);
        autoTPPSetting.setIs_auto_accept(is_auto_accept);
        autoTPPSettingRepository.updateAutoTPPSetting(autoTPPSetting);
    }


    public void setAutoTPPSettingRepository(AutoTppSettingRepository autoTPPSettingRepository) {
        this.autoTPPSettingRepository = autoTPPSettingRepository;
    }

    public AutoTppSettingRepository getAutoTPPSettingRepository() {
        return autoTPPSettingRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }



}
