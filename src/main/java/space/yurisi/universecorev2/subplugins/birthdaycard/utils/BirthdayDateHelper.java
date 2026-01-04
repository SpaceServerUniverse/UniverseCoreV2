package space.yurisi.universecorev2.subplugins.birthdaycard.utils;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.subplugins.birthdaycard.BirthdayCard;
import space.yurisi.universecorev2.utils.Message;
import space.yurisi.universecorev2.utils.NumberUtils;

import java.time.DateTimeException;
import java.time.MonthDay;

public class BirthdayDateHelper {

    public static boolean isValidDate(String monthArg, String dayArg) {
        boolean isMonthMissing = monthArg == null;
        boolean isDayMissing = dayArg == null;

        boolean isMonthInvalid = !NumberUtils.isNumeric(monthArg);
        boolean isDayInvalid = !NumberUtils.isNumeric(dayArg);

        return isMonthMissing
                || isDayMissing
                || isMonthInvalid
                || isDayInvalid;
    }

    public static MonthDay parseMonthDay(String monthArg, String dayArg) throws DateTimeException {
        int month = Integer.parseInt(monthArg);
        int day = Integer.parseInt(dayArg);
        return MonthDay.of(month, day);
    }
}
