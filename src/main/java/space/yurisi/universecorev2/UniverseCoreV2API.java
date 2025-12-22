package space.yurisi.universecorev2;

import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.DatabaseConnector;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.DatabaseManagerV2;

public class UniverseCoreV2API {
    private static UniverseCoreV2API api;

    private final DatabaseManager manager;

    private final DatabaseManagerV2 managerv2;

    private final DatabaseConnector connector;

    private final SessionFactory sessionFactory;

    public UniverseCoreV2API(DatabaseConnector connector){
        this.connector = connector;
        this.sessionFactory = connector.getSessionFactory();
        this.manager = new DatabaseManager(sessionFactory);
        this.managerv2 = new DatabaseManagerV2(sessionFactory);
        api = this;
    }

    /**
     * データベースマネージャーの取得
     *
     * @return manager データベースマネージャー
     */
    public DatabaseManager getDatabaseManager(){
        return this.manager;
    }

    /**
     * データベースマネージャーV2の取得
     *
     * @return manager データベースマネージャー
     */
    public DatabaseManagerV2 getDatabaseManagerV2(){
        return this.managerv2;
    }

    /**
     * 自身のインスタンスを取得
     * @return api UniverseCoreAPI
     */
    public static UniverseCoreV2API getInstance(){
        return api;
    }
}