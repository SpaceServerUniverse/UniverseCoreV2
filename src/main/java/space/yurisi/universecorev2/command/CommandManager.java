package space.yurisi.universecorev2.command;

import space.yurisi.universecorev2.UniverseCoreV2;

public class CommandManager {

    private final UniverseCoreV2 main;

    public CommandManager(UniverseCoreV2 main){
        this.main = main;
        init();
    }

    private void init(){
        main.getCommand("password").setExecutor(new passwordCommand());
        main.getCommand("pplayer").setExecutor(new pplayerCommand());
        main.getCommand("lobby").setExecutor(new lobbyCommand());
        main.getCommand("giveu").setExecutor(new giveuCommand());
    }
}
