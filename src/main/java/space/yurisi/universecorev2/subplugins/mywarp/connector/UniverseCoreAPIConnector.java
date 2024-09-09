package space.yurisi.universecorev2.subplugins.mywarp.connector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.database.repositories.MywarpRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.mywarp.file.Config;

import java.util.List;

public class UniverseCoreAPIConnector {

    private UserRepository userRepository;

    private MywarpRepository mywarpRepository;

    protected final List<String> denyWorlds;

    public UniverseCoreAPIConnector(DatabaseManager databaseManager, Config config){
        setUserRepository(databaseManager.getUserRepository());
        setMywarpRepository(databaseManager.getMywarpRepository());
        this.denyWorlds = config.getDenyWorlds();
    }

    public Boolean isExistsMywarpName(Player player, String warp_name) throws UserNotFoundException, MywarpNotFoundException{
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        List<Mywarp> mywarp_list = mywarpRepository.getMywarpFromUserId(user_id);
        for (Mywarp mywarp : mywarp_list) {
            if (mywarp.getName().equals(warp_name)) {
                return true;
            }
        }
        return false;
    }

    public Mywarp getMywarpFromName(Player player, String warp_name) throws UserNotFoundException, MywarpNotFoundException{
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        try {
            List<Mywarp> mywarp_list = mywarpRepository.getMywarpFromUserId(user_id);
            for (Mywarp mywarp : mywarp_list) {
                if (mywarp.getName().equals(warp_name)) {
                    return mywarp;
                }
            }
            throw new MywarpNotFoundException("マイワープが見つかりませんでした。");
        } catch (MywarpNotFoundException e) {
            throw new MywarpNotFoundException("マイワープが見つかりませんでした。");
        }
    }

    public void createMywarp(Player player, String warp_name, Boolean is_private){
        mywarpRepository.createMywarp(player, warp_name, is_private);
    }

    public void deleteMywarp(Mywarp mywarp) throws MywarpNotFoundException{
        mywarpRepository.deleteMywarp(mywarp);
    }

    public List<Mywarp> getMywarpList(Player player) throws UserNotFoundException, MywarpNotFoundException{
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        List<Mywarp> mywarpList = mywarpRepository.getMywarpFromUserId(user_id);
        if(mywarpList.isEmpty()){
            throw new MywarpNotFoundException("マイワープが見つかりませんでした。");
        }
        return mywarpList;
    }

    public List<Mywarp> getPublicMywarpListFromName(String target_user_name) throws MywarpNotFoundException, UserNotFoundException{
        Long user_id = userRepository.getPrimaryKeyFromPlayerName(target_user_name);
        List<Mywarp> mywarpList = mywarpRepository.getPublicMywarpFromUserId(user_id);
        if(mywarpList.isEmpty()){
            throw new MywarpNotFoundException("公開マイワープが見つかりませんでした。");
        }
        return mywarpList;
    }

    public void teleportMywarp(Player player, Mywarp mywarp) throws MywarpNotFoundException{
        Long x = mywarp.getX();
        Long y = mywarp.getY();
        Long z = mywarp.getZ();
        String world_name = mywarp.getWorld_name();
        Location location = new Location(player.getServer().getWorld(world_name), x, y, z);
        player.teleport(location);
    }

    public Boolean isDenyWorld(String world_name){
        return denyWorlds.contains(world_name);
    }

    public void updateMywarp(Mywarp mywarp) throws MywarpNotFoundException {
        mywarpRepository.updateMywarp(mywarp);
    }

    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void setMywarpRepository(MywarpRepository mywarpRepository) {
        this.mywarpRepository = mywarpRepository;
    }
}
