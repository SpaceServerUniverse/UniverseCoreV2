package space.yurisi.universecorev2.subplugins.universediscord.event;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

import java.util.List;

public class DiscordEvent extends ListenerAdapter {

    private String discordChannelId;

    public DiscordEvent(String discordChannelId) {
        this.discordChannelId = discordChannelId;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || !event.isFromType(ChannelType.TEXT)) {
            return;
        }

        if (!event.getChannel().getId().equals(discordChannelId)) {
            return;
        }

        Member member = event.getMember();
        String content = event.getMessage().getContentDisplay();
        List<Message.Attachment> file = event.getMessage().getAttachments();

        if (member == null) {
            return;
        }

        if(!file.isEmpty()) {
            UniverseDiscordMessage.sendMessageToMinecraft(member, "(ファイルを添付しました)");
            return;
        }

        if (content.equals("!playerlist")) {
            // すでに環境変数で指定されたチャンネルであることは確認済みなので, 暗黙的キャストでテキストチャンネル *として* 扱う
            UniverseDiscordMessage.sendStatusMessageToMinecraft(event.getChannel().asTextChannel());
            return;
        }

        UniverseDiscordMessage.sendMessageToMinecraft(member, content);
    }

}