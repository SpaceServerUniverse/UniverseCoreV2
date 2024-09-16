package space.yurisi.universecorev2.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.UniverseCoreV2;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class LoginEvent implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        String name = player.getName();
        UUID uuid = player.getUniqueId();

        DatabaseManager databaseManager = UniverseCoreV2API.getInstance().getDatabaseManager();
        UserRepository userRepository = databaseManager.getUserRepository();
        if (!userRepository.existsUserFromUUID(uuid)) {
            userRepository.createUser(player);
        }

        User user;
        try{
            user = userRepository.getUserFromUUID(uuid);
        } catch (UserNotFoundException e){
            player.kick(Component.text("§cユーザーデータの読み込み時にエラーが発生しました。管理者に報告してください。"));
            return;
        }


        if(!Objects.equals(user.getName(), name)){
            user.setName(name);
        }

        user.setUpdated_at(new Date());
        userRepository.updateUser(user);
    }
}
