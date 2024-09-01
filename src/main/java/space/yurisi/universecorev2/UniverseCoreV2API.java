package space.yurisi.universecorev2;

import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.DatabaseConnector;
import space.yurisi.universecorev2.database.DatabaseManager;

public class UniverseCoreV2API {
    private static UniverseCoreV2API api;

    private final DatabaseManager manager;

    private final DatabaseConnector connector;

    private final SessionFactory sessionFactory;

    public UniverseCoreV2API(DatabaseConnector connector){
        this.connector = connector;
        this.sessionFactory = connector.getSessionFactory();
        this.manager = new DatabaseManager(sessionFactory);
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
     * 自身のインスタンスを取得
     * @return api UniverseCoreAPI
     */
    public static UniverseCoreV2API getInstance(){
        return api;
    }
}