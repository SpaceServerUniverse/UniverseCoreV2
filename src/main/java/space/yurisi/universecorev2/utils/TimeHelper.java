package space.yurisi.universecorev2.utils;

import space.yurisi.universecorev2.utils.time_type.TimeType;
import java.time.LocalTime;

public class TimeHelper {

    /**
     * 現在時刻をチェックし、その時間帯を返します。
     *
     * @return TimeType 時間帯 -- MORNING: 朝, AFTERNOON: 昼, EVENING: 夜
     */
    public static TimeType checkTime() {
        LocalTime now = LocalTime.now();

        if (now.isBefore(LocalTime.NOON)) {
            return TimeType.MORNING;
        } else if (now.isBefore(LocalTime.of(18, 0))) {
            return TimeType.AFTERNOON;
        } else {
            return TimeType.EVENING;
        }
    }

}
