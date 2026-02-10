package space.yurisi.universecorev2.subplugins.universediscord.config;

import org.jetbrains.annotations.NotNull;

public class DiscordConfiguration {

    private final String discordAPIToken;
    private final String guildId;
    private final String channelId;

    public DiscordConfiguration(String discordAPIToken, String guildId, String channelId) {
        this.discordAPIToken = discordAPIToken;
        this.guildId = guildId;
        this.channelId = channelId;
    }

    public @NotNull String getDiscordAPIToken() {
        return discordAPIToken;
    }

    public @NotNull String getGuildId() {
        return guildId;
    }

    public @NotNull String getChannelId() {
        return channelId;
    }

}
