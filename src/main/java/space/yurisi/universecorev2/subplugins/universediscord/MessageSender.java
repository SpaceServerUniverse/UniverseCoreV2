package space.yurisi.universecorev2.subplugins.universediscord;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class MessageSender {

    private static final Logger logger = Bukkit.getLogger();

    private static String discordInviteURL = "https://discord\\.gg/[a-zA-Z0-9]+";
    private static Pattern pattern = Pattern.compile(discordInviteURL);

    private MessageSender() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void sendToMinecraft(@NotNull Member member, @NotNull String message) {
        if (!isSafeMessage(message)) {
            logger.warning("[UniverseDiscord] 安全でないメッセージが検出されました。送信を中止します。");
            return;
        }

        String displayName = formatMemberName(member);
        String formattedMessage = String.format("§b[Discord] §e<%s> §f%s", displayName, message);

        Bukkit.broadcast(Component.text(formattedMessage));
    }

    private static @NotNull String formatMemberName(@NotNull Member member) {
        return Optional.ofNullable(member.getNickname())
                .map(nickname -> member.getUser().getName() + " (" + nickname + ")")
                .orElse(member.getUser().getName());
    }

    public static void sendPlayerMessage(
            @NotNull Player player,
            @NotNull TextChannel channel,
            @NotNull String message
    ) {
        try {
            Location location = player.getLocation();
            String worldName = Optional.ofNullable(location.getWorld())
                    .map(WorldInfo::getName)
                    .orElse("UnknownWorld");

            String formattedMessage = String.format("[%s@%s] %s", player.getName(), worldName, message);
            channel.sendMessage(formattedMessage)
                    .queue(
                            success -> logger.info("[UniverseDiscord] メッセージが正常に送信されました。"),
                            error -> logger.severe("[UniverseDiscord] メッセージの送信に失敗しました: " + error.getMessage())
                    );
        } catch (Exception e) {
            logger.warning("[UniverseDiscord] メッセージの送信中にエラーが発生しました: " + e.getMessage());
        }
    }

    public static void sendPlayerList(@NotNull TextChannel channel) {
        try {
            String players = Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .collect(Collectors.joining(", "));

            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            int maxPlayers = Bukkit.getMaxPlayers();

            String formattedMessage = String.format("オンラインプレイヤー (%d/%d): %s", onlinePlayers, maxPlayers, players);
            channel.sendMessage(formattedMessage)
                    .queue(
                            success -> {},
                            error -> logger.severe("[UniverseDiscord] プレイヤーリストの送信に失敗しました: " + error.getMessage())
                    );
        } catch (Exception e) {
            logger.warning("[UniverseDiscord] プレイヤーリストの送信中にエラーが発生しました: " + e.getMessage());
        }
    }

    /**
     * 送信しようとした文字列が安全かどうかを判定します.
     * 以下のチェックを行います.
     * - Discord の招待リンクが含まれていないか
     *
     * @param message チェックする文字列
     * @return boolean 安全な文字列かどうか
     */
    private static boolean isSafeMessage(String message) {
        return !pattern.matcher(message).matches();
    }

}
