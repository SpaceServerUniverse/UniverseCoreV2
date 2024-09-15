package space.yurisi.universecorev2.subplugins.universediscord.event.player;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

public class JoinEvent implements Listener {

    private TextChannel discordChannel;

    public JoinEvent(TextChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        UniverseDiscordMessage.sendJoinMessageToDiscord(event.getPlayer(), discordChannel);
    }
}
