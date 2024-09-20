package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;

public class UniverseDiscordChannel {

    public static void updateDiscordChannelTopic(TextChannel channel, int playerCount) {
        channel.getManager().setTopic(playerCount + "人が接続中").queue();
    }

}
