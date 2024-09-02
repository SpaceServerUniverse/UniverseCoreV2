package space.yurisi.universecorev2.subplugins.levelsystem.manager;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystem;
import space.yurisi.universecorev2.subplugins.levelsystem.command.addexpCommand;

public class CommandManager {

    public CommandManager(UniverseCoreV2 core){
        core.getCommand("addexp").setExecutor(new addexpCommand());
    }
}
