package space.yurisi.universecorev2.subplugins.universeeconomy.command;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomy;

public class CommandManager {
    public CommandManager(UniverseCoreV2 core) {
        init(core);
    }

    private void init(UniverseCoreV2 core) {
        core.getCommand("money").setExecutor(new moneyCommand());
        core.getCommand("mymoney").setExecutor(new mymoneyCommand());
        core.getCommand("addmoney").setExecutor(new addmoneyCommand());
        core.getCommand("reducemoney").setExecutor(new reducemoneyCommand());
        core.getCommand("pay").setExecutor(new payCommand());
        core.getCommand("seemoney").setExecutor(new seemoneyCommand());
        core.getCommand("setmoney").setExecutor(new setmoneyCommand());
    }
}
