package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.subplugins.birthdaycard.utils.PlayerUtils;
import space.yurisi.universecorev2.utils.Message;

import java.util.List;
import java.util.UUID;

public class listSubCommand implements BirthdayCardSubCommand{
    @Override
    public boolean execute(Player player, String[] args) {
        int page = 1;

        if(args.length >= 2){
            try {
                page = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignore) {
                //適当な文字列だったら1でいいため。
            }
        }

        page = Math.max(1, page);

        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);

        int limit = 10;
        int offset = (page - 1) * limit;

        List<BirthdayData> birthdayDataList = repo.getAllToPaginate(offset, limit);

        if(birthdayDataList.isEmpty()) {
            Message.sendNormalMessage(player, BirthdayCard.PREFIX, "誕生日データが見つかりませんでした");
            return true;
        }

        StringBuilder message = new StringBuilder("登録されている誕生日:\n");
        for (BirthdayData bd : birthdayDataList) {
            UUID playerUUID = UUID.fromString(bd.getUuid());
            message.append(PlayerUtils.getPlayerNameByUuid(playerUUID))
                    .append("の誕生日は")
                    .append(bd.getMonth()).append("月")
                    .append(bd.getDay()).append("日\n");
        }
        message.append(page).append("ページ");
        Message.sendNormalMessage(player, BirthdayCard.PREFIX, message.toString());
        return true;
    }
}
