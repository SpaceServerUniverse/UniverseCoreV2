package space.yurisi.universecorev2.subplugins.universejob.listener.block;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJobAPI;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;

import java.util.Map;

public class NetherrackBreakEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent event) {
        if(event.isCancelled()){
            return;
        }

        Player player = event.getPlayer();

        if(!player.getGameMode().equals(GameMode.SURVIVAL)){
            return;
        }

        if(!(event.getBlock().getType() == Material.NETHERRACK)){
            return;
        }

        JobType jobType;

        try{
            jobType = UniverseJobAPI.getInstance().getJobType(player);
        } catch (JobTypeNotFoundException e) {
            return;
        }
        if(jobType != JobType.HIGH_LEVEL_MINER){
            return;
        }

        // TODO: モデルのメモリ化
//        try {
//            UniverseEconomyAPI.getInstance().addMoney(player, 1L, "ネザーラック採掘");
//        } catch (UserNotFoundException | MoneyNotFoundException | CanNotAddMoneyException | ParameterException e) {
//            return;
//        }
    }
}
