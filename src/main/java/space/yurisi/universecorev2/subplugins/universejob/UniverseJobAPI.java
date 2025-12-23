package space.yurisi.universecorev2.subplugins.universejob;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;
import space.yurisi.universecorev2.subplugins.universejob.manager.PlayerJobManager;

public class UniverseJobAPI {

    private PlayerJobManager manager;

    private UniverseCoreAPIConnector connector;

    private static UniverseJobAPI api;

    public static UniverseJobAPI getInstance() {
        return api;
    }

    public UniverseJobAPI(PlayerJobManager manager, UniverseCoreAPIConnector connector) {
        this.manager = manager;
        this.connector = connector;
        api = this;
    }

    public JobType getJobType(Player player) throws JobTypeNotFoundException {
        JobType jobType;
        try {
            jobType = manager.getPlayerJobType(player);
        } catch (JobTypeNotFoundException e) {
            throw new JobTypeNotFoundException(e.getMessage());
        }
        return jobType;
    }
}
