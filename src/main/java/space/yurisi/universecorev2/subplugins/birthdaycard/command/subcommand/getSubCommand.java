package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.utils.Message;

public class getSubCommand implements BirthdayCardSubCommand{
    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length < 2) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday get <プレイヤー名>");
            return true;
        }

        String targetName = args[1];

        Player online = Bukkit.getPlayerExact(targetName);
        OfflinePlayer target = (online != null) ? online : Bukkit.getOfflinePlayer(targetName);

        //オンラインか、オフラインであれば過去に参加しているか確認
        if(online == null && !target.hasPlayedBefore()){
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "プレイヤーが存在しません。");
            return true;
        }

        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);

        if(!repo.existsBirthdayData(target.getUniqueId())) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "データが見つかりません");
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "自分自身にメッセージを書くことはできません");
            return true;
        }

        space.yurisi.universecorev2.item.birthday_card.BirthdayCard birthdayCard = new space.yurisi.universecorev2.item.birthday_card.BirthdayCard(target);
        player.getInventory().addItem(birthdayCard.getItem(target));

        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日カードを入手しました。メッセージを記入して署名して下さい。");
        Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[送る]", ClickEvent.runCommand("/birthday send"), "お誕生日カードを送る");
        return true;
    }
}
