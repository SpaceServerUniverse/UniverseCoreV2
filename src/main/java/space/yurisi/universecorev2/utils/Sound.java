package space.yurisi.universecorev2.utils;

import org.bukkit.entity.Player;

public class Sound {

    public static void sendSuccessSound(Player player){
        player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1);
    }

    public static void sendErrorSound(Player player){
        //TODO
    }

    public static void sendTeleportSound(Player player){
        player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }
}
