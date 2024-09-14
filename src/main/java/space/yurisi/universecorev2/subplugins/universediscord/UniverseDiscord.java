package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universediscord.event.DiscordEvent;
import space.yurisi.universecorev2.subplugins.universediscord.event.EventManager;
import space.yurisi.universecorev2.file.Config;
import space.yurisi.universecorev2.subplugins.universediscord.exception.ChannelNotFoundException;
import space.yurisi.universecorev2.subplugins.universediscord.exception.GuildNotFoundException;
import space.yurisi.universecorev2.subplugins.universediscord.exception.JDANotReadyException;

import java.util.List;

public class UniverseDiscord implements SubPlugin {

    private Config config;

    @Override
    public void onEnable(UniverseCoreV2 core) {
        this.config = new Config(core);

        List<GatewayIntent> intents = List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        JDA jda = JDABuilder.createDefault(config.getDiscordBotToken(), intents).addEventListeners(new DiscordEvent()).setAutoReconnect(true).build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new JDANotReadyException("JDA is not ready!");
        }

        Guild guild = jda.getGuildById(config.getDiscordGuildId());
        if (guild == null) {
            throw new GuildNotFoundException("Guild not found with ID: " + config.getDiscordGuildId());
        }

        TextChannel channel = guild.getTextChannelById(config.getDiscordChannelId());
        if (channel == null) {
            throw new ChannelNotFoundException("Channel not found with ID: " + config.getDiscordChannelId());
        }

        new EventManager(core, channel);
    }

    public @NotNull Config getPluginConfig() {
        return this.config;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "UniverseDiscord";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
