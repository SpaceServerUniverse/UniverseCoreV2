package space.yurisi.universecorev2.subplugins.universediscord.event;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

public class ServerEvent implements Listener {

    private TextChannel discordChannel;

    public ServerEvent(TextChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLoad(ServerLoadEvent event) {
        UniverseDiscordMessage.sendEventMessageToDiscord(discordChannel, "サーバーが起動しました!", 0x2396FF);
    }

}
