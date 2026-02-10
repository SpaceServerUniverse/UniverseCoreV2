package space.yurisi.universecorev2.subplugins.universediscord.event;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;
import space.yurisi.universecorev2.subplugins.universediscord.MessageSender;

import java.util.logging.Logger;

public class MinecraftMessageListener implements Listener {

    private static final Logger logger = Bukkit.getLogger();
    private static final LegacyComponentSerializer SERIALIZER =
            LegacyComponentSerializer.legacy(LegacyComponentSerializer.AMPERSAND_CHAR);
    private final TextChannel discordChannel;

    public MinecraftMessageListener(TextChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        String message = SERIALIZER.serialize(event.getPlayer().name()) + " がサーバーに参加しました。";
        discordChannel.sendMessage(message)
                .queue(
                        success -> {},
                        error -> logger.warning("Discordへのメッセージ送信に失敗しました: " + error.getMessage())
                );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        String message = SERIALIZER.serialize(event.getPlayer().name()) + " がサーバーから退出しました。";
        discordChannel.sendMessage(message)
                .queue(
                        success -> {},
                        error -> logger.warning("Discordへのメッセージ送信に失敗しました: " + error.getMessage())
                );
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncChatEvent event) {
        String message = PlainTextComponentSerializer.plainText().serialize(event.message());
        MessageSender.sendPlayerMessage(event.getPlayer(), discordChannel, message);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onServerLoad(ServerLoadEvent event) {
        String message = "サーバーが正常に起動しました。";
        discordChannel.sendMessage(message)
                .queue(
                        success -> {},
                        error -> logger.warning("Discordへのメッセージ送信に失敗しました: " + error.getMessage())
                );
    }

}
