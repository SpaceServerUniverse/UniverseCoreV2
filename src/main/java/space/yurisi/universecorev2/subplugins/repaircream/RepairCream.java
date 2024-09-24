package space.yurisi.universecorev2.subplugins.repaircream;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.repaircream.command.repairCommand;
import space.yurisi.universecorev2.subplugins.universeutilcommand.dice.DiceCommand;

public class RepairCream implements SubPlugin {
    @Override
    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("repair").setExecutor(new repairCommand());
    }

    @Override
    public void onDisable() {

    }

    @Override
    public String getName() {
        return "RepairCream";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
