package space.yurisi.universecorev2.subplugins.rankcounter.event;

import org.apache.commons.lang3.time.DateUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.subplugins.rankcounter.manager.CounterModelManager;
import space.yurisi.universecorev2.database.models.count.PlayerCount;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlayerCountEvents implements Listener {

    private final CounterModelManager manager;

    public PlayerCountEvents(CounterModelManager manager){
        this.manager = manager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        PlayerCount playerCount = manager.get(player).getPlayerCount();

        Date last_login_date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setLenient(false);
        try {
            last_login_date = simpleDateFormat.parse(playerCount.getLast_login_date());
        }catch (ParseException exception){
            throw new RuntimeException(exception);
        }

        Date now = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

        //今日がlast_login_dateより後か確認して、loginを増やす
        //もし1日前にもログインしてたらconsecutive_loginも増やす
        if (now.after(last_login_date)){
            playerCount.setLogin(playerCount.getLogin() + 1);

            Calendar yesterday = Calendar.getInstance();
            yesterday.setTime(new Date());
            yesterday.add(Calendar.DAY_OF_MONTH, -1);

            if(simpleDateFormat.format(last_login_date).equals(simpleDateFormat.format(yesterday.getTime()))){
                playerCount.setConsecutive_login(playerCount.getConsecutive_login() + 1);
            }

            playerCount.setLast_login_date(simpleDateFormat.format(new Date()));
        }
    }
}
