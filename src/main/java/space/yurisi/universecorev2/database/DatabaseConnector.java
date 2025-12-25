package space.yurisi.universecorev2.database;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.logging.LogFactory;
import org.flywaydb.core.internal.logging.javautil.JavaUtilLogCreator;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import space.yurisi.universecorev2.database.models.*;
import space.yurisi.universecorev2.database.models.count.*;

import static org.hibernate.cfg.AvailableSettings.*;

public class DatabaseConnector {

    private String jdbc;
    private String user;
    private String password;

    private SessionFactory sessionFactory;

    public DatabaseConnector(String host, int port, String user, String password) {
        this.user = user;
        this.password = password;
        this.jdbc = "jdbc:mysql://" + host + ":" + port;
        init();
    }

    private void init() {
        migrate();
        this.sessionFactory = buildSessionFactory();
    }

    private void migrate() {
        LogFactory.setLogCreator(new JavaUtilLogCreator());
        Flyway.configure(getClass().getClassLoader())
                .dataSource(jdbc, this.user, this.password)
                .baselineVersion("2023.09.20.01")
                .locations("classpath:db/migration/")
                .schemas("flyway_schema")
                .load().migrate();
    }

    private SessionFactory buildSessionFactory() {
        Configuration config = new Configuration();
        config = registerAnnotatedClasses(config);
        config = registerProperties(config);
        return config.buildSessionFactory();
    }

    private Configuration registerAnnotatedClasses(Configuration configuration) {
        return configuration.addAnnotatedClass(User.class)
                .addAnnotatedClass(Money.class)
                .addAnnotatedClass(MoneyHistory.class)
                .addAnnotatedClass(Land.class)
                .addAnnotatedClass(LandPermission.class)
                .addAnnotatedClass(Mywarp.class)
                .addAnnotatedClass(AutoTppSetting.class)
                .addAnnotatedClass(PlayerLevel.class)
                .addAnnotatedClass(PlayerNormalLevel.class)
                .addAnnotatedClass(Position.class)
                .addAnnotatedClass(ContainerProtect.class)
                .addAnnotatedClass(UserPosition.class)
                .addAnnotatedClass(Count.class)
                .addAnnotatedClass(KillDeathCount.class)
                .addAnnotatedClass(LifeCount.class)
                .addAnnotatedClass(OreCount.class)
                .addAnnotatedClass(PlayerCount.class)
                .addAnnotatedClass(CustomName.class)
                .addAnnotatedClass(Market.class)
                .addAnnotatedClass(ChestShop.class)
                .addAnnotatedClass(Ammo.class)
                .addAnnotatedClass(ReceiveBox.class)
                .addAnnotatedClass(BirthdayData.class)
                .addAnnotatedClass(BirthdayMessages.class)
                .addAnnotatedClass(LoginBonus.class)
                .addAnnotatedClass(LevelReward.class)
                .addAnnotatedClass(Job.class)
                .addAnnotatedClass(SpaceShip.class);
    }

    private Configuration registerProperties(Configuration configuration) {
        return configuration.setProperty(DRIVER, "com.mysql.cj.jdbc.Driver")
                .setProperty(URL, jdbc + "/SpaceServerUniverse")
                .setProperty(USER, this.user)
                .setProperty(PASS, this.password)
                .setProperty(POOL_SIZE, "1")
                .setProperty(SHOW_SQL, "false")
                .setProperty(FORMAT_SQL, "false")
                .setProperty(JDBC_TIME_ZONE, "Asia/Tokyo")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.hbm2ddl.auto", "none");
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void close() {
        if (this.sessionFactory != null) {
            this.sessionFactory.close();
        }
    }
}
