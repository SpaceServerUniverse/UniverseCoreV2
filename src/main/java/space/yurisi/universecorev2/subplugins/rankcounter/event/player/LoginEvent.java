package space.yurisi.universecorev2.subplugins.rankcounter.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;
import space.yurisi.universecorev2.subplugins.rankcounter.utils.Counters;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.exception.*;

public class LoginEvent implements Listener {

    private CounterModelManager manager;

    public LoginEvent(CounterModelManager manager){
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        DatabaseManager databaseManager = UniverseCoreV2API.getInstance().getDatabaseManager();

        try {
            User user = databaseManager.getUserRepository().getUserFromUUID(player.getUniqueId());

            if (!databaseManager.getCountRepository().existsCountFromUser(user)) {
                databaseManager.getCountRepository().createCount(user);
            }
            Count count = databaseManager.getCountRepository().getCountFromUser(user);

            createCounters(count, databaseManager);
            Counters counters = new Counters(count);

            manager.register(player, counters);

        } catch (UserNotFoundException exception) {
            player.kick(Component.text("エラーが発生しました。管理者に報告してください。 エラーコード: RC01"));
        } catch (CountNotFoundException exception) {
            player.kick(Component.text("エラーが発生しました。管理者に報告してください。 エラーコード: RC02"));
        } catch (PlayerCountNotFoundException | KillDeathCountNotFoundException | OreCountNotFoundException |LifeCountNotFoundException exception) {
            player.kick(Component.text("エラーが発生しました。管理者に報告してください。 エラーコード: RC03"));
        }
    }

    private void createCounters(Count count, DatabaseManager databaseManager) {
        if (!databaseManager.getKillDeathCountRepository().existsKillDeathCount(count)) {
            databaseManager.getKillDeathCountRepository().createKillDeathCount(count);
        }

        if (!databaseManager.getOreCountRepository().existsOreCount(count)) {
            databaseManager.getOreCountRepository().createOreCount(count);
        }

        if (!databaseManager.getLifeCountRepository().existsLifeCount(count)) {
            databaseManager.getLifeCountRepository().createLifeCount(count);
        }

        if (!databaseManager.getPlayerCountRepository().existsPlayerCount(count)) {
            databaseManager.getPlayerCountRepository().createPlayerCount(count);
        }
    }
}
