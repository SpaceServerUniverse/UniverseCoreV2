package space.yurisi.universecorev2.api;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.exception.LuckPermsGroupNotFoundException;

import java.util.Objects;

public class LuckPermsWrapper {

    public void initLuckPerms() {
        Bukkit.getServicesManager().getRegistration(LuckPermsWrapper.class);
    }

    /**
     * プレイヤーが指定したグループに所属しているかどうか確認します
     * @param player プレイヤー
     * @param group グループ名
     * @return 所属している場合はtrue、所属していない場合はfalse
     */
    public static boolean isUserInGroup(Player player, String group) {
        return player.hasPermission("group." + group);
    }

    /**
     * プレイヤーが管理者グループまたは開発者グループに所属しているかどうか確認します
     * @param player　プレイヤー
     * @return いずれかに所属している場合はtrue、所属していない場合はfalse
     */
    public static boolean isUserInAdminOrDevGroup(Player player) {
        return player.hasPermission("group.admin") || player.hasPermission("group.developer");
    }

    /**
     * オフラインプレイヤーが指定したグループに所属しているかどうか確認します
     * @param playerName プレイヤー名
     * @param group グループ名
     * @return 所属している場合はtrue、所属していない場合はfalse
     */
    public static boolean isOfflineUserInGroup(String playerName, String group) {
        return Objects.requireNonNull(Bukkit.getOfflinePlayer(playerName).getPlayer()).hasPermission("group." + group);
    }

    /**
     * 指定した文字列に合致する LuckPerms のグループを取得します
     * @param groupName グループ名
     * @return グループ
     * @throws LuckPermsGroupNotFoundException グループが見つからなかった場合
     */
    public static Group getLuckPermsGroup(String groupName) {
        LuckPerms luckPerms = LuckPermsProvider.get();
        Group result = luckPerms.getGroupManager().getGroup(groupName);

        if (result == null) {
            throw new LuckPermsGroupNotFoundException("Group not found");
        }

        return result;
    }

}
