package space.yurisi.universecorev2.subplugins.achievement;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.achievement.command.AchievementCommand;
import space.yurisi.universecorev2.subplugins.achievement.data.config.AchievementConfig;

public final class Achievement implements SubPlugin {

    @Override
    public void onEnable(UniverseCoreV2 core) {
        new AchievementConfig(core);
        core.getCommand("achievement").setExecutor(new AchievementCommand());
    }

    @Override
    public void onDisable() {
        //NOOP
    }

    @Override
    public String getName() {
        return "Achievement";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
