package space.yurisi.universecorev2.subplugins.birthdaycard;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.birthdaycard.command.BirthdayCardCommand;

public class BirthdayCard implements SubPlugin {
    public static String PREFIX = "[お誕生日AI]";

    public void onEnable(UniverseCoreV2 core) {
        core.getCommand("birthday").setExecutor(new BirthdayCardCommand());
    }

    public void onDisable() {

    }

    public String getName() {
        return "BirthdayCard";
    }

    public String getVersion() {
        return "1.0.0";
    }
}
