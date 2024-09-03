package space.yurisi.universecorev2.subplugins.universeeconomy.event.player;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.UserNotFoundException;

public class LoginEvent implements Listener {

    private DatabaseManager databaseManager;

    public LoginEvent(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        UserRepository userRepository = databaseManager.getUserRepository();
        MoneyRepository moneyRepository = databaseManager.getMoneyRepository();

        Player player = event.getPlayer();

        try {
            User user = userRepository.getUserFromUUID(player.getUniqueId());
            if(!moneyRepository.existsMoneyFromUserId(user.getId())){
                moneyRepository.createMoney(user);
            }
        }catch (UserNotFoundException e){
            player.kick(Component.text("マネーの作成でエラーが発生しました。管理者に報告してください。"));

        }
    }
}
