package space.yurisi.universecorev2.subplugins.universediscord.event.player;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

public class QuitEvent implements Listener {

    private TextChannel discordChannel;

    public QuitEvent(TextChannel discordChannel) {
        this.discordChannel = discordChannel;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        UniverseDiscordMessage.sendQuitMessageToDiscord(event.getPlayer(), discordChannel);
    }
}
