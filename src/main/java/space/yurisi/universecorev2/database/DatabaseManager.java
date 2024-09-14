package space.yurisi.universecorev2.database;

import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.repositories.*;
import space.yurisi.universecorev2.database.repositories.count.*;

public class DatabaseManager {

    private final UserRepository userRepository;
    private final MoneyRepository moneyRepository;
    private final MoneyHistoryRepository moneyHistoryRepository;
    private final LandRepository landRepository;
    private final LandPermissionRepository landPermissionRepository;
    private final MywarpRepository mywarpRepository;
    private final PlayerLevelRepository playerLevelRepository;
    private final PlayerNormalLevelRepository playerNormalLevelRepository;
    private final PositionRepository positionRepository;
    private final UserPositionRepository userPositionRepository;
    private final CountRepository countRepository;
    private final KillDeathCountRepository killDeathCountRepository;
    private final LifeCountRepository lifeCountRepository;
    private final OreCountRepository oreCountRepository;
    private final PlayerCountRepository playerCountRepository;
    private final MarketRepository marketRepository;

    public DatabaseManager(SessionFactory sessionFactory) {
        this.userRepository = new UserRepository(sessionFactory);
        this.moneyHistoryRepository = new MoneyHistoryRepository(sessionFactory);
        this.moneyRepository = new MoneyRepository(sessionFactory, getMoneyHistoryRepository());
        this.landRepository = new LandRepository(sessionFactory, getLandRepository());
        this.landPermissionRepository = new LandPermissionRepository(sessionFactory);
        this.mywarpRepository = new MywarpRepository(sessionFactory, getUserRepository());
        this.playerLevelRepository = new PlayerLevelRepository(sessionFactory);
        this.playerNormalLevelRepository = new PlayerNormalLevelRepository(sessionFactory);
        this.positionRepository = new PositionRepository(sessionFactory);
        this.userPositionRepository = new UserPositionRepository(sessionFactory);
        this.countRepository = new CountRepository(sessionFactory);
        this.killDeathCountRepository = new KillDeathCountRepository(sessionFactory);
        this.lifeCountRepository = new LifeCountRepository(sessionFactory);
        this.oreCountRepository = new OreCountRepository(sessionFactory);
        this.playerCountRepository = new PlayerCountRepository(sessionFactory);
        this.marketRepository = new MarketRepository(sessionFactory);
    }

    /**
     * ユーザーリポジトリを取得
     *
     * @return UserRepository
     */
    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * お金リポジトリの取得
     *
     * @return MoneyRepository
     */
    public MoneyRepository getMoneyRepository() {
        return moneyRepository;
    }

    /**
     * お金履歴リポジトリを取得
     *
     * @return MoneyHistoryRepository
     */
    public MoneyHistoryRepository getMoneyHistoryRepository() {
        return moneyHistoryRepository;
    }

    /**
     * 土地保護のリポジトリを取得
     *
     * @return LandRepository
     */
    public LandRepository getLandRepository() {
        return landRepository;
    }

    /**
     * 土地保護の権限リポジトリを取得
     *
     * @return LandPermissionRepository
     */
    public LandPermissionRepository getLandPermissionRepository() {
        return landPermissionRepository;
    }

    /**
     * マイワープリポジトリを取得
     *
     * @return MywarpRepository
     */
    public MywarpRepository getMywarpRepository() {
        return mywarpRepository;
    }

    /**
     * プレイヤーレベルリポジトリを取得
     *
     * @return PlayerLevelRepository
     */
    public PlayerLevelRepository getPlayerLevelRepository() {
        return playerLevelRepository;
    }

    /**
     * プレイヤーノーマルレベルリポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public PlayerNormalLevelRepository getPlayerNormalLevelRepository() {
        return playerNormalLevelRepository;
    }

    /**
     * 役職リポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public PositionRepository getPositionRepository() {
        return positionRepository;
    }

    /**
     * プレイヤー役職リポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public UserPositionRepository getUserPositionRepository() {
        return userPositionRepository;
    }

    /**
     * カウントリポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public CountRepository getCountRepository() {
        return countRepository;
    }

    /**
     * キルデスカウントリポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public KillDeathCountRepository getKillDeathCountRepository() {
        return killDeathCountRepository;
    }

    /**
     * 生活カウントリポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public LifeCountRepository getLifeCountRepository() {
        return lifeCountRepository;
    }

    /**
     * 鉱石カウントリポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public OreCountRepository getOreCountRepository() {
        return oreCountRepository;
    }

    /**
     * プレイヤーカウントリポジトリを取得
     *
     * @return PlayerNormalLevelRepository
     */
    public PlayerCountRepository getPlayerCountRepository() {
        return playerCountRepository;
    }

    /**
     * マーケットリポジトリ取得
     *
     * @return MarketRepository
     */
    public MarketRepository getMarketRepository() { return marketRepository; }
}
