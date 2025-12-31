package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.utils.Message;

import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.UUID;

import static space.yurisi.universecorev2.subplugins.birthdaycard.utils.BirthdayDateHelper.parseMonthDay;

public class registerconfirmSubCommand implements BirthdayCardSubCommand{

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length < 3) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday register <月> <日>");
            return true;
        }

        MonthDay monthDay;
        try {
            monthDay = parseMonthDay(args[1], args[2]);
        } catch (DateTimeException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "エラーが発生しました。月または日が無効です");
            return true;
        }

        UUID uuidMC = player.getUniqueId();
        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);

        if(repo.existsBirthdayData(uuidMC)){
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "既に誕生日が登録されています");
            return true;
        }

        repo.createBirthdayData(uuidMC, monthDay.getMonthValue(), monthDay.getDayOfMonth());
        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "お誕生日を登録しました");
        return true;
    }
}
