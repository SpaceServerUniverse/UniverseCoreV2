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

public class registerSubCommand implements BirthdayCardSubCommand {
    private static final int ARG_MONTH = 1;
    private static final int ARG_DAY   = 2;

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length < 3) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "/birthday register <月> <日>");
            return true;
        }

        MonthDay registerMonthDay;

        try {
            registerMonthDay = parseMonthDay(args[ARG_MONTH], args[ARG_DAY]);
        } catch (DateTimeException | NumberFormatException e) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "月または日が無効です。確認してください。");
            return true;
        }

        UUID uuidMC = player.getUniqueId();
        BirthdayCardRepository repo = UniverseCoreV2API.getInstance().getDatabaseManagerV2().get(BirthdayCardRepository.class);

        if (repo.existsBirthdayData(uuidMC)) {
            Message.sendErrorMessage(player, BirthdayCard.PREFIX, "既に誕生日が登録されています");
            return true;
        }

        Message.sendSuccessMessage(player, BirthdayCard.PREFIX, "一度登録すると変更することはできません");
        Message.sendNormalMessage(player, BirthdayCard.PREFIX, "[登録]", ClickEvent.runCommand("/birthday registerconfirm " + registerMonthDay.getMonthValue() + " " + registerMonthDay.getDayOfMonth()), "誕生日を登録します");

        return true;
    }
}
