package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.utils.Message;

public class checkSubCommand implements BirthdayCardSubCommand {
    @Override
    public boolean execute(Player player, String[] args) {
        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);
        if (args.length < 2) {
            try {
                BirthdayData data = repo.getBirthdayData(player.getUniqueId());
                Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "あなたの誕生日は" + data.getMonth() + "月" + data.getDay() + "日");
            } catch (BirthdayDataNotFoundException e) {
                Message.sendErrorMessage(player, BirthdayCard.PREFIX, "あなたの誕生日が登録されていません");
            }
            return true;
        }

        String targetName = args[1];

        Player online = Bukkit.getPlayerExact(targetName);
        OfflinePlayer targetOfflinePlayer = Bukkit.getOfflinePlayer(targetName);
        if(targetOfflinePlayer == null){
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが存在しません");
            return true;
        }
        OfflinePlayer target = (online != null) ? online : targetOfflinePlayer;

        //オンラインか、オフラインであれば過去に参加しているか確認
        if (online == null && !target.hasPlayedBefore()) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが存在しません。");
            return true;
        }

        try {
            BirthdayData data = repo.getBirthdayData(target.getUniqueId());
            Message.sendSuccessMessage(player, BirthdayCard.PREFIX, target.getName() + "の誕生日は" + data.getMonth() + "月" + data.getDay() + "日");
        } catch (BirthdayDataNotFoundException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, target.getName() + "の誕生日が登録されていません");
        }
        return true;
    }

    protected void sendMessage(Player player, Player target){

    }
}
