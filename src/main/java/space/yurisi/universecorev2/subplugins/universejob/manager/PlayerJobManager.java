package space.yurisi.universecorev2.subplugins.universejob.manager;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;

import java.util.HashMap;
import java.util.UUID;

public class PlayerJobManager {
    private HashMap<UUID, Integer> playerJobs = new HashMap<>();

    private UniverseJob main;

    public PlayerJobManager(UniverseJob main) {
        this.main = main;
    }

    public void registerPlayer(Player player, int jobID){
        playerJobs.put(player.getUniqueId(), jobID);
    }

    public Integer getPlayerJobID(Player player){
        return playerJobs.get(player.getUniqueId());
    }

    public JobType getPlayerJobType(Player player) throws JobTypeNotFoundException {
        Integer jobID = this.playerJobs.get(player.getUniqueId());
        if(jobID == null) {
            throw new JobTypeNotFoundException("Player job ID not found for player: " + player.getName());
        }
        return JobType.getJobTypeFromID(jobID);
    }

    public boolean isExistPlayerJob(Player player){ return this.playerJobs.containsKey(player.getUniqueId()); }

    public void unregisterPlayer(Player player){
        this.playerJobs.remove(player.getUniqueId());
    }

}
