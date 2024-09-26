package space.yurisi.universecorev2.subplugins.chestshop.utils;


public class NumberUtils {

    public static boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

