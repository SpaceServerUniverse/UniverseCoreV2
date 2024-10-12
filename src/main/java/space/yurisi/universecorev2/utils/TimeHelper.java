package space.yurisi.universecorev2.utils;

import space.yurisi.universecorev2.utils.time_type.TimeType;
import java.time.LocalTime;

public class TimeHelper {

    /**
     * 現在時間をチェックし、その時間帯を表すTimeType列挙型を返します。
     *
     * @return TimeType 時間帯
     */
    public static TimeType checkTime() {
        LocalTime now = LocalTime.now();

        if (now.isBefore(LocalTime.of(5, 0))) {
            return TimeType.LATE_NIGHT;
        } else if (now.isBefore(LocalTime.of(8, 0))) {
            return TimeType.EARLY_MORNING;
        } else if (now.isBefore(LocalTime.of(10, 0))) {
            return TimeType.MORNING;
        } else if (now.isBefore(LocalTime.of(16, 0))) {
            return TimeType.AFTERNOON;
        } else if (now.isBefore(LocalTime.of(20, 0))) {
            return TimeType.EVENING;
        } else {
            return TimeType.NIGHT;
        }
    }

}
