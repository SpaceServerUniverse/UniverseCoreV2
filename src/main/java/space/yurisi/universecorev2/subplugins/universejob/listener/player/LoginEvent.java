package space.yurisi.universecorev2.subplugins.universejob.listener.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.database.models.Job;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.exception.PlayerJobNotFoundException;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;

public class LoginEvent implements Listener {

    private UniverseJob main;

    public LoginEvent(UniverseJob main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        JobRepository jobRepository = main.getJobRepository();

        Job job;

        try {

            job = jobRepository.getJobFromPlayer(player);

        } catch (PlayerJobNotFoundException e) {
            job = jobRepository.createJob(player);
        }

        int jobID = job.getJob_id();

        main.getPlayerJobManager().registerPlayer(player, jobID);
    }
}
