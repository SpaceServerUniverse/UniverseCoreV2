package space.yurisi.universecorev2.api;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LuckPermsWrapper {

    public void initLuckPerms() {
        Bukkit.getServicesManager().getRegistration(LuckPermsWrapper.class);
    }

    public static boolean isUserInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    public static boolean isOfflineUserInGroup(String playerName, String group) {
        return Objects.requireNonNull(Bukkit.getOfflinePlayer(playerName).getPlayer()).hasPermission("group." + group);
    }

    public static Group getLuckPermsGroup(String groupName) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        Group result = luckPerms.getGroupManager().getGroup(groupName);

        if (result == null) {
            throw new NullPointerException("Group not found");
        }

        return result;
    }

}
