package space.yurisi.universecorev2.subplugins.salute.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public abstract class SaluteBaseCommand implements CommandExecutor {

    private Component[] messages;

    public void setMessages(Component[] messages){
        this.messages = messages;
    }

    private int getRandom(){
        Random rand = new Random();
        return rand.nextInt(messages.length);
    }

    public Component getMessage(){
        return this.messages[getRandom()];
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return false;
    }
}
