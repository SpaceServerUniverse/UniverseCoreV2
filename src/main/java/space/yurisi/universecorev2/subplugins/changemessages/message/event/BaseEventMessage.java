package space.yurisi.universecorev2.subplugins.changemessages.message.event;

import net.kyori.adventure.text.Component;

import java.util.Random;

public class BaseEventMessage {

    protected Component[] messages;

    private int getRandom(Component[] components){
        Random rnd = new Random();
        return rnd.nextInt(components.length);
    }

    public Component getMessage() {
        int i = this.getRandom(messages);
        return messages[i];
    }

    protected void setMessages(Component[] components){
        this.messages = components;
    }
}
