package space.yurisi.universecorev2.subplugins.universejob;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universejob.manager.EventManager;
import space.yurisi.universecorev2.subplugins.universejob.manager.PlayerJobManager;

public class UniverseJob implements SubPlugin {

    private static UniverseJob instance;

    private JobRepository jobRepository;

    private UniverseCoreAPIConnector connector;

    private PlayerJobManager playerJobManager;

    public static UniverseJob getInstance() {return instance;}

    public JobRepository getJobRepository() { return jobRepository; }

    public UniverseCoreAPIConnector getConnector() { return connector; }

    public PlayerJobManager getPlayerJobManager() { return playerJobManager; }

    public void onEnable(UniverseCoreV2 core) {
        instance = this;
        this.connector = new UniverseCoreAPIConnector();
        this.jobRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(JobRepository.class);
        playerJobManager = new PlayerJobManager(this);
        new EventManager(core, this);
    }

    public void onDisable() {

    }

    @Override
    public String getName() {
        return "UniverseJob";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
