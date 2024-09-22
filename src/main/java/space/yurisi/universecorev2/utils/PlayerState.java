package space.yurisi.universecorev2.utils;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.*;
import space.yurisi.universecorev2.database.repositories.*;
import space.yurisi.universecorev2.exception.*;


public final class PlayerState {
    public static void setDisplayName(Player player){
        DatabaseManager dbManager = UniverseCoreV2API.getInstance().getDatabaseManager();
        UserRepository userRepo = dbManager.getUserRepository();
        PlayerNormalLevelRepository normalLevelRepo = dbManager.getPlayerNormalLevelRepository();
        CustomNameRepository customNameRepo = dbManager.getCustomNameRepository();
        PositionRepository positionRepo = dbManager.getPositionRepository();
        UserPositionRepository userPositionRepo = dbManager.getUserPositionRepository();


        String name = "";
        User user;
        try {
            user = userRepo.getUserFromUUID(player.getUniqueId());
        } catch (UserNotFoundException e) {
            return;
        }

        try {
            PlayerNormalLevel level = normalLevelRepo.getPlayerNormalLevelFromUser(user);
            String displayLevel = String.valueOf(level.getLevel());
            if(level.getLevel() == 1000){
                displayLevel = "§l☆MAX☆§r";
            }
            name += "<§eLV."+displayLevel+"§f>";
        } catch (PlayerNormalLevelNotFoundException e) {
            name += "<§eLV.1§r>";
        }

        try {
            UserPosition userPosition = userPositionRepo.getUserPositionFromUser(user);
            String position = positionRepo.getNameFromId(userPosition.getPosition_id());
            name += "“§b"+position+"§r“";
        } catch (UserPositionNotFoundException | PositionNotFoundException e) {
        }

        try {
            CustomName customName = customNameRepo.getCustomNameFromUserId(user.getId());
            name += "["+customName.getDisplay_custom_tag()+"§r]";
        } catch (CustomNameNotFoundException e) {
        }

        name += player.getName();
        player.setDisplayName(name);
    }

    public static boolean hasInventorySpace(Player player) {
        int emptySlot = player.getInventory().firstEmpty();
        return emptySlot != -1;
    }
}
