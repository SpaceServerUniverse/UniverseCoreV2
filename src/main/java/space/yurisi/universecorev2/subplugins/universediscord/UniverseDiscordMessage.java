package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.utils.Message;

import java.util.stream.Collectors;

public class UniverseDiscordMessage {

    /** Minecraft へのメッセージ */

    public static void sendMessageToMinecraft(Member discordMember, String discordMessage) {
        String name_format;

        if (!Message.isSafeMessage(discordMessage)) {
            return;
        }

        if (discordMember.getNickname() == null) {
            name_format = discordMember.getUser().getName();
        } else {
            name_format = discordMember.getUser().getName() + " (" + discordMember.getNickname() + ")";
        }

        Bukkit.broadcast(Component.text("§a[Discord] §r<" + name_format + "> " + discordMessage));
    }


    /** Discord へのメッセージ */

    public static void sendMessageToDiscord(Player player, TextChannel discordChannel, String message) {
        Location location = player.getLocation();
        String world_name = location.getWorld().getName();

        discordChannel.sendMessage("[" + player.getName() + "@" + world_name + "]" + " " + message).queue();
    }

    public static void sendEventMessageToDiscord(TextChannel discordChannel, String message, int color) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setDescription(message);
        embed.setColor(color);

        discordChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void sendJoinMessageToDiscord(Player player, TextChannel discordChannel) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("入室");
        embed.setDescription(player.getName() + " がログインしました");
        embed.setThumbnail("https://mc-heads.net/head/" + player.getUniqueId());
        embed.setColor(0x2AFF5C);

        discordChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void sendQuitMessageToDiscord(Player player, TextChannel discordChannel) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("退出");
        embed.setDescription(player.getName() + " がログアウトしました");
        embed.setThumbnail("https://mc-heads.net/head/" + player.getUniqueId());
        embed.setColor(0x687EFF);

        discordChannel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void sendStatusMessageToMinecraft(TextChannel discordChannel) {
        String players = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
        int online_players = Bukkit.getOnlinePlayers().size();
        int max_players = Bukkit.getMaxPlayers();

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("サーバー情報");
        embed.setDescription("```\n" + players + "\n```");
        embed.setFooter("現在のプレイヤー数: " + online_players + " / " + max_players);
        embed.setColor(0xFFA74B);

        discordChannel.sendMessageEmbeds(embed.build()).queue();

    }

}
