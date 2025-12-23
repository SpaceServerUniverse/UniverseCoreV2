package space.yurisi.universecorev2.subplugins.universejob.listener.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.UnknownLevelModeException;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;

public class LoginEvent implements Listener {

    private UniverseJob main;

    public LoginEvent(UniverseJob main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        JobRepository jobRepository = main.getJobRepository();

        try {
            int jobID;

            if(!jobRepository.isExistJob(player)){
                jobRepository.createJob(player);
                jobID = 0;
            } else {
                jobID = jobRepository.getJobID(player);
            }

            main.getPlayerJobManager().registerPlayer(player, jobID);

        } catch (JobTypeNotFoundException e) {
            player.kick(Component.text("§c[職業AI] 職業データの取得に失敗しました。管理者にお問い合わせください。"));
            throw new RuntimeException(e);
        }
    }
}
