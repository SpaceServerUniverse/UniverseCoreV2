package space.yurisi.universecorev2.subplugins.universejob.menu;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.exception.JobTypeNotFoundException;
import space.yurisi.universecorev2.menu.BaseMenu;
import space.yurisi.universecorev2.subplugins.levelsystem.LevelSystemAPI;
import space.yurisi.universecorev2.subplugins.levelsystem.exception.PlayerDataNotFoundException;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;
import space.yurisi.universecorev2.subplugins.universejob.menu.item.ChangeJobMenuItem;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.window.Window;

import java.util.ArrayList;
import java.util.List;

public class ChangeJobMenu implements BaseMenu {

    private List<Item> changeableJobItems;

    @Override
    public void sendMenu(Player player){
        changeableJobItems = new ArrayList<>();
        int level;
        try{
            level = LevelSystemAPI.getInstance().getLevel(player);
        } catch (PlayerDataNotFoundException e) {
            return;
        }

        JobRepository jobRepository = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(JobRepository.class);

        List<Integer> changeableJobIndexes = JobType.getJobIDLowerThanLevel(level);
        for(Integer index : changeableJobIndexes){
            JobType jobtype;
            try{
                jobtype = JobType.getJobTypeFromID(index);
                changeableJobItems.add(new ChangeJobMenuItem(jobtype, jobRepository));
            } catch (JobTypeNotFoundException e){
                continue;
            }
        }

        Gui gui = PagedGui.items()
                .setStructure(
                        "x x x x x x x x x",
                        "x x x x x x x x x",
                        "x x x x x x x x x")
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(changeableJobItems)
                .build();

        xyz.xenondevs.invui.window.Window window = Window.single()
                .setViewer(player)
                .setGui(gui)
                .setTitle("職業変更メニュー")
                .build();

        window.open();
    }

}
