package space.yurisi.universecorev2.subplugins.universediscord.event;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.subplugins.universediscord.MessageSender;

import java.util.List;
import java.util.Optional;

public class DiscordMessageListener extends ListenerAdapter {

    private final String targetChannelId;

    public DiscordMessageListener(String targetChannelId) {
        this.targetChannelId = targetChannelId;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || !event.isFromType(ChannelType.TEXT)) {
            return;
        }

        if (!event.getChannel().getId().equals(targetChannelId)) {
            return;
        }

        Optional<Member> memberOpt = Optional.ofNullable(event.getMember());
        if (memberOpt.isEmpty()) {
            return;
        }

        Member member = memberOpt.get();
        String content = event.getMessage().getContentDisplay();
        List<Message.Attachment> attachments = event.getMessage().getAttachments();

        if (!attachments.isEmpty()) {
            String formattedMessage = content.isEmpty() ? "(添付ファイルを送信しました)": content + "\n(添付ファイルを送信しました)";
            MessageSender.sendToMinecraft(member, formattedMessage);
            return;
        }

        if ("!playerlist".equals(content)) {
            MessageSender.sendPlayerList(event.getChannel().asTextChannel());
            return;
        }

        MessageSender.sendToMinecraft(member, content);
    }

}
