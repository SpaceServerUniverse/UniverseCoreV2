package space.yurisi.universecorev2.subplugins.levelsystem.utils;

import java.util.Calendar;

public class DayOfWeek {

    private static DayOfWeek instance;

    public DayOfWeek(){
        instance = this;
    }

    public boolean isHoliday(){
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        return (week == Calendar.SATURDAY) || week == (Calendar.SUNDAY);
    }

   public void checkHoliday(){
        isHoliday();
   }

    public static DayOfWeek getInstance(){
        return instance;
    }
}
