package space.yurisi.universecorev2.subplugins.levelsystem;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.manager.PlayerLevelDataManager;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode.LevelModes;
import space.yurisi.universecorev2.exception.PlayerLevelNotFoundException;
import space.yurisi.universecorev2.exception.PlayerNormalLevelNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class LevelSystemAPI {

    private PlayerLevelDataManager manager;

    private UniverseCoreAPIConnector connector;

    private static LevelSystemAPI api;

    public LevelSystemAPI(PlayerLevelDataManager manager, UniverseCoreAPIConnector connector) {
        this.manager = manager;
        this.connector = connector;
        api = this;
    }

    /**
     * オンラインのプレイヤーに経験値を与えます。
     * @param player
     * @param exp
     * @throws PlayerDataNotFoundException
     */
    public void addExp(Player player, int exp) throws PlayerDataNotFoundException {
        if (!this.manager.exists(player)) {
            throw new PlayerDataNotFoundException("プレイヤーデータが見つかりません。プレイヤーがオンラインではない可能性があります。");
        }
        this.manager.get(player).addExp(exp);
    }

    /**
     * オンラインのプレイヤーのレベルを取得します
     * (オンラインのプレイヤーはメモリに保存されているため)
     * @param player
     * @return Level
     * @throws PlayerDataNotFoundException
     */
    public int getLevel(Player player) throws PlayerDataNotFoundException {
        if (!this.manager.exists(player)) {
            throw new PlayerDataNotFoundException("プレイヤーデータが見つかりません。プレイヤーがオンラインではない可能性があります。");
        }
        return this.manager.get(player).getLevel();
    }

    /**
     * プレイヤー名からデータベースに保存されているレベルを取得します
     *
     * @param name
     * @return Level
     * @throws UserNotFoundException
     * @throws PlayerNormalLevelNotFoundException
     */
    public int getLevelFromPlayerName(String name) throws UserNotFoundException, PlayerNormalLevelNotFoundException {
        //TODO LevelMode
        return connector.getNormalLevelFromPlayerName(name).getLevel();
    }

    /**
     * オンラインのプレイヤーのレベルモードを取得します
     * (オンラインのプレイヤーはメモリに保存されているため)
     * @param player
     * @return LevelMode
     * @throws PlayerDataNotFoundException
     */
    public LevelModes getLevelMode(Player player) throws PlayerDataNotFoundException {
        if (this.manager.exists(player)) {
            throw new PlayerDataNotFoundException("プレイヤーデータが見つかりません。プレイヤーがオンラインではない可能性があります。");
        }
        return this.manager.get(player).getLevelMode();
    }

    /**
     * プレイヤー名からデータベースに保存されているレベルモードを取得します
     * @param name
     * @return LevelMode
     * @throws UserNotFoundException
     * @throws PlayerLevelNotFoundException
     */
    public LevelModes getLevelModeFromPlayerName(String name) throws UserNotFoundException, PlayerLevelNotFoundException {
        int level_mode = connector.getLevelFromPlayerName(name).getLevel_mode();
        return LevelModes.fromInteger(level_mode);
    }

    /**
     * 現在のメモリのデータをデータベースに保存します
     */
    public void saveAll(){
        manager.saveAll();
    }

    /**
     * インスタンスを取得します
     * @return LevelSystemAPI
     */
    public static LevelSystemAPI getInstance() {
        return api;
    }
}
