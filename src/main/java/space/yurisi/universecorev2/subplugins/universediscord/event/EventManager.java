package space.yurisi.universecorev2.subplugins.universediscord.event;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import space.yurisi.universecorev2.UniverseCoreV2;

public class EventManager {

    public EventManager(UniverseCoreV2 core, TextChannel channel){
        init(core, channel);
    }

    private void init(UniverseCoreV2 core, TextChannel channel){
        core.getServer().getPluginManager().registerEvents(new PlayerEvent(channel), core);
        core.getServer().getPluginManager().registerEvents(new ServerEvent(channel), core);
    }
}
