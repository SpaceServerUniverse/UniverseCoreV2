package space.yurisi.universecorev2.subplugins.universeutilcommand;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeutilcommand.salute.kCommand;
import space.yurisi.universecorev2.subplugins.universeutilcommand.salute.otiCommand;
import space.yurisi.universecorev2.subplugins.universeutilcommand.salute.otuCommand;
import space.yurisi.universecorev2.subplugins.universeutilcommand.suicide.suCommand;
import space.yurisi.universecorev2.subplugins.universeutilcommand.dice.DiceCommand;
import space.yurisi.universecorev2.subplugins.universeutilcommand.trash.TrashCommand;

public class UniverseUtilCommand implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        // Salute
        core.getCommand("k").setExecutor(new kCommand());
        core.getCommand("oti").setExecutor(new otiCommand());
        core.getCommand("otu").setExecutor(new otuCommand());

        // suicide
        core.getCommand("su").setExecutor(new suCommand());

        // dice
        core.getCommand("dice").setExecutor(new DiceCommand());

        // trash
        core.getCommand("trash").setExecutor(new TrashCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public String getName() {
        return "UniverseUtilCommand";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
