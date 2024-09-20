package space.yurisi.universecorev2.subplugins.universediscord.event;

import io.papermc.paper.advancement.AdvancementDisplay;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordChannel;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

public class PlayerEvent implements Listener {

    private TextChannel discordChannel;

    public PlayerEvent(TextChannel discordChannel) {
        this.discordChannel = discordChannel;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        UniverseDiscordMessage.sendJoinMessageToDiscord(event.getPlayer(), discordChannel);
        UniverseDiscordChannel.updateDiscordChannelTopic(discordChannel, event.getPlayer().getServer().getOnlinePlayers().size());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        UniverseDiscordMessage.sendQuitMessageToDiscord(event.getPlayer(), discordChannel);
        UniverseDiscordChannel.updateDiscordChannelTopic(discordChannel, event.getPlayer().getServer().getOnlinePlayers().size());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event) {
        String message = LegacyComponentSerializer.legacy(LegacyComponentSerializer.AMPERSAND_CHAR).serialize(event.originalMessage());

        UniverseDiscordMessage.sendMessageToDiscord(event.getPlayer(), discordChannel, message);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Component deathMessage = event.deathMessage();
        if (deathMessage == null) {
            return;
        }

        String reason = LegacyComponentSerializer.legacy(LegacyComponentSerializer.AMPERSAND_CHAR).serialize(deathMessage);
        UniverseDiscordMessage.sendEventMessageToDiscord(discordChannel, player.getName() + "は死亡した 理由: " + reason, 0xFF362F);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDoneAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        AdvancementDisplay advancementDisplay = event.getAdvancement().getDisplay();

        if (advancementDisplay == null) {
            return;
        }

        String advancementName = LegacyComponentSerializer.legacy(LegacyComponentSerializer.AMPERSAND_CHAR).serialize(advancementDisplay.title());
        UniverseDiscordMessage.sendEventMessageToDiscord(discordChannel, player.getName() + "は進捗【" + advancementName + "】を達成した!", 0xFFF318);
    }

}
