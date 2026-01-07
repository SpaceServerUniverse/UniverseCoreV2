package space.yurisi.universecorev2.database;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.database.repositories.LevelRewardRepository;
import space.yurisi.universecorev2.database.repositories.SlotRepository;
import space.yurisi.universecorev2.database.repositories.SpaceShipRepository;

import java.util.HashMap;
import java.util.Map;

public final class DatabaseManagerV2 {
    private final SessionFactory sessionFactory;
    private final Map<Class<?>, Object> repos = new HashMap<>();

    public DatabaseManagerV2(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

        register(new LevelRewardRepository(sessionFactory));
        register(new JobRepository(sessionFactory));
        register(new SpaceShipRepository(sessionFactory));
        register(new SlotRepository(sessionFactory));
    }

    private <T> void register(T repo) {
        repos.put(repo.getClass(), repo);
    }

    public <T> T get(Class<T> type) {
        return type.cast(repos.get(type));
    }
}