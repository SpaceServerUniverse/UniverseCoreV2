package space.yurisi.universecorev2.subplugins.universeslot;

import space.yurisi.universecorev2.UniverseCoreV2;
import space.yurisi.universecorev2.subplugins.SubPlugin;
import space.yurisi.universecorev2.subplugins.universeslot.command.SlotCommand;
import space.yurisi.universecorev2.subplugins.universeslot.listener.EventManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.PlayerStatusManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.RoleManager;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotLocationManager;
import space.yurisi.universecorev2.subplugins.universeslot.core.Roller;
import space.yurisi.universecorev2.subplugins.universeslot.manager.SlotStatusManager;

import java.util.Objects;

public class UniverseSlot implements SubPlugin {

    private static UniverseSlot instance;
    public static UniverseSlot getInstance() {
        return instance;
    }

    private SlotLocationManager slotLocationManager;
    public SlotLocationManager getSlotLocationManager() {
        return slotLocationManager;
    }

    private PlayerStatusManager playerStatusManager;
    public PlayerStatusManager getPlayerStatusManager() {
        return playerStatusManager;
    }

    private SlotStatusManager slotStatusManager;
    public SlotStatusManager getSlotStatusManager() {
        return slotStatusManager;
    }

    private RoleManager roleManager;
    public RoleManager getRoleManager() {
        return roleManager;
    }

    private Roller roller;
    public Roller getRoller() {
        return roller;
    }

    public void onEnable(UniverseCoreV2 core) {
        instance = this;
        playerStatusManager = new PlayerStatusManager();
        slotStatusManager = new SlotStatusManager();
        slotLocationManager = new SlotLocationManager();
        roleManager = new RoleManager();
        roller = new Roller();
        new EventManager(core, this);

        Objects.requireNonNull(core.getCommand("slot")).setExecutor(new SlotCommand());
    }

    public void onDisable() {
    }

    @Override
    public String getName() {
        return "Slot";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
