package space.yurisi.universecorev2.subplugins.universejob;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.constants.UniverseItemKeyString;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universejob.command.JobCommand;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;
import space.yurisi.universecorev2.subplugins.universejob.manager.EventManager;
import space.yurisi.universecorev2.subplugins.universejob.manager.PlayerJobManager;

import java.util.List;

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
        core.getCommand("job").setExecutor(new JobCommand());
        new UniverseJobAPI(playerJobManager, connector);
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
