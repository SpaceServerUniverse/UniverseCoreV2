package space.yurisi.universecorev2.subplugins.levelsystem.manager;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.UnknownLevelModeException;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.PlayerLevelData;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode.LevelModes;
import space.yurisi.universecorev2.database.models.PlayerLevel;
import space.yurisi.universecorev2.database.models.PlayerNormalLevel;
import space.yurisi.universecorev2.exception.PlayerLevelNotFoundException;
import space.yurisi.universecorev2.exception.PlayerNormalLevelNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;

import java.util.HashMap;
import java.util.UUID;

public class PlayerLevelDataManager {
    private HashMap<UUID, PlayerLevelData> data = new HashMap<>();

    private LevelSystem main;

    public PlayerLevelDataManager(LevelSystem main) {
        this.main = main;
    }

    public void register(Player player) throws UnknownLevelModeException {
        try {
            PlayerLevelData playerLevelData;
            PlayerLevel playerLevel = main.getConnector().getPlayerLevel(player);
            LevelModes levelMode = LevelModes.fromInteger(playerLevel.getLevel_mode());

            //TODO Levelmodes

            if (!levelMode.equals(LevelModes.NORMAL)) {
                throw new UnknownLevelModeException("レベルモードが存在しません");
            }

            PlayerNormalLevel playerNormalLevel = main.getConnector().getPlayerNormalLevel(player);
            playerLevelData = new PlayerLevelData(
                    player,
                    levelMode,
                    playerLevel.getTotal_exp(),
                    playerNormalLevel.getLevel(),
                    playerNormalLevel.getExp(),
                    playerNormalLevel.getLevel_mode_total_exp(),
                    playerNormalLevel.getExp_for_next_level()
            );

            this.data.put(player.getUniqueId(), playerLevelData);
        } catch (UserNotFoundException | PlayerLevelNotFoundException | PlayerNormalLevelNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerLevelData get(Player player) {
        return this.data.get(player.getUniqueId());
    }

    public boolean exists(Player player) {
        return this.data.containsKey(player.getUniqueId());
    }

    public void unregister(Player player) {
        this.data.remove(player.getUniqueId());
    }


    public void saveAll(){
        this.data.forEach((key, value) -> {
            main.getConnector().save(value);
        });

    }
}
