package space.yurisi.universecorev2.subplugins.levelsystem.listener.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.UnknownLevelModeException;
import space.yurisi.universecorev2.subplugins.levelsystem.utils.connector.UniverseCoreAPIConnector;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class LoginEvent implements Listener {

    private LevelSystem main;

    public LoginEvent(LevelSystem main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent event) {
        UniverseCoreAPIConnector connector = main.getConnector();
        Player player = event.getPlayer();

        try {
            if (!connector.existsPlayerLevel(player)) {
                connector.createLevel(player);
            }
            if (!connector.existsPlayerNormalLevel(player)) {
                connector.createNormalLevel(player);
            }

            main.getPlayerLevelDataManager().register(player);

        } catch (UserNotFoundException | UnknownLevelModeException e) {
            player.kick(Component.text("レベルデータがうまく生成できませんでした。管理者に報告してください。"));
            throw new RuntimeException(e);
        }
    }
}
