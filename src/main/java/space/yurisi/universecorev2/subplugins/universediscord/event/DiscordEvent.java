package space.yurisi.universecorev2.subplugins.universediscord.event;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import space.yurisi.universecorev2.subplugins.universediscord.UniverseDiscordMessage;

public class DiscordEvent extends ListenerAdapter {

    private String discordChannelId;

    public DiscordEvent(String discordChannelId) {
        this.discordChannelId = discordChannelId;
    }

    // TODO: 画像の送信に対応する
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

        if (member == null) {
            return;
        }

        UniverseDiscordMessage.sendMessageToMinecraft(member, content);
    }

}
