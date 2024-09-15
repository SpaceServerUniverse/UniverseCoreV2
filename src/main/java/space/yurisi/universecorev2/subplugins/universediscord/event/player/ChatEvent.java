package space.yurisi.universecorev2.subplugins.universediscord.event.player;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

public class ChatEvent implements Listener {

    private TextChannel discordChannel;

    public ChatEvent(TextChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        String message = LegacyComponentSerializer.legacy(LegacyComponentSerializer.AMPERSAND_CHAR).serialize(event.originalMessage());
        UniverseDiscordMessage.sendMessageToDiscord(event.getPlayer(), discordChannel, message);
    }
}
