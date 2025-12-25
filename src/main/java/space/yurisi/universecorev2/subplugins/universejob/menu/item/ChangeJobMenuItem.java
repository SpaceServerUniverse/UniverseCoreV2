package space.yurisi.universecorev2.subplugins.universejob.menu.item;

import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import space.yurisi.universecorev2.database.repositories.JobRepository;
import space.yurisi.universecorev2.exception.AlreadyOnJobException;
import space.yurisi.universecorev2.exception.NotEnoughDurationJobChangeException;
import space.yurisi.universecorev2.exception.PlayerJobNotFoundException;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;
import space.yurisi.universecorev2.subplugins.universejob.constants.JobType;
import space.yurisi.universecorev2.utils.Message;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

import java.util.List;

public class ChangeJobMenuItem extends AbstractItem {

    private JobType jobtype;
    private JobRepository jobRepository;

    public ChangeJobMenuItem(JobType jobtype, JobRepository jobRepository) {
        this.jobtype = jobtype;
        this.jobRepository = jobRepository;
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(jobtype.getModel())
                .setDisplayName(jobtype.getDisplayName())
                .setLegacyLore(List.of(jobtype.getDescription()));
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull org.bukkit.entity.Player player, @NotNull org.bukkit.event.inventory.InventoryClickEvent event) {
        try{
            jobRepository.updateJob(player, jobtype.getId());
            UniverseJob.getInstance().getPlayerJobManager().registerPlayer(player, jobtype.getId());
            Message.sendSuccessMessage(player, "[職業AI]", "あなたの職業が " + jobtype.getDisplayName() + " に変更されました。");
            player.closeInventory();
        } catch (PlayerJobNotFoundException e){
            Message.sendErrorMessage(player, "[職業AI]", "職業の変更に失敗しました。管理者にお問い合わせください。");
        } catch (AlreadyOnJobException e){
            Message.sendErrorMessage(player, "[職業AI]", "既にその職業に就いています。");
        } catch (NotEnoughDurationJobChangeException e){
            Message.sendErrorMessage(player, "[職業AI]", "職業は一度変更すると1週間変更できません。");
        }
    }
}
