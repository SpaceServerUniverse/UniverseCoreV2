package space.yurisi.universecorev2.subplugins.birthdaycard.command.subcommand;

import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.repositories.BirthdayCardRepository;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.utils.Message;

import java.time.DateTimeException;
import java.time.MonthDay;
import java.util.UUID;

import static space.yurisi.universecorev2.subplugins.birthdaycard.utils.BirthdayDateHelper.parseMonthDay;

public class registerSubCommand implements BirthdayCardSubCommand{

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length < 3) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday register <月> <日>");
            return false;
        }

        MonthDay registerMonthDay;

        try {
            registerMonthDay = parseMonthDay(args[1], args[2]);
        } catch (DateTimeException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "月または日が無効です。確認してください。");
            return true;
        }

        UUID uuidMC = player.getUniqueId();
        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);

        try {
            repo.getBirthdayData(uuidMC);
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "既に誕生日が登録されています");
        } catch (BirthdayDataNotFoundException e) {
            Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "一度登録すると変更することはできません");
            Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[登録]", ClickEvent.runCommand("/birthday registerconfirm " + registerMonthDay.getMonthValue() + " " + registerMonthDay.getDayOfMonth()), "誕生日を登録します");
        }
        return true;
    }
}
