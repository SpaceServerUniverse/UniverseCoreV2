package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universediscord.event.DiscordEvent;
import space.yurisi.universecorev2.subplugins.universediscord.event.EventManager;
import space.yurisi.universecorev2.subplugins.universediscord.exception.DiscordChannelNotFoundException;
import space.yurisi.universecorev2.subplugins.universediscord.exception.DiscordGuildNotFoundException;
import space.yurisi.universecorev2.subplugins.universediscord.exception.DiscordJDANotReadyException;
import space.yurisi.universecorev2.subplugins.universediscord.file.Config;

import java.util.List;
import java.util.Objects;

public class UniverseDiscord implements SubPlugin {

    private Config config;

    @Override
    public void onEnable(UniverseCoreV2 core) {
        this.config = new Config(core);

        if(Objects.equals(config.getDiscordBotToken(), "")){
            Bukkit.getLogger().info("トークンが書かれていないのでUniverseDiscordは無効化されました。");
            onDisable();
            return;
        }

        List<GatewayIntent> intents = List.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        JDA jda = JDABuilder.createLight(config.getDiscordBotToken(), intents).addEventListeners(new DiscordEvent(config.getDiscordChannelId())).setAutoReconnect(true).build();

        try {
            jda.awaitReady();
        } catch (InterruptedException e) {
            throw new DiscordJDANotReadyException("JDA is not ready!");
        }

        Guild guild = jda.getGuildById(config.getDiscordGuildId());
        if (guild == null) {
            throw new DiscordGuildNotFoundException("Guild not found with ID: " + config.getDiscordGuildId());
        }

        TextChannel channel = guild.getTextChannelById(config.getDiscordChannelId());
        if (channel == null) {
            throw new DiscordChannelNotFoundException("Channel not found with ID: " + config.getDiscordChannelId());
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
