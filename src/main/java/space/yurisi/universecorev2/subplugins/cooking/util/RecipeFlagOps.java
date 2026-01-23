package space.yurisi.universecorev2.subplugins.cooking.util;

public final class RecipeFlagOps {

    public static final int MAX_RECIPES = 256;
    public static final int RECIPE_BYTES = 32;

    private RecipeFlagOps() {}

    public static byte[] add(byte[] flags, int index) {
        rangeCheck(index);
        int byteIndex = index >> 3;
        int bitIndex  = index & 0b111;
        flags[byteIndex] |= (byte) (1 << bitIndex);
        return flags.clone();
    }

    public static byte[] remove(byte[] flags, int index) {
        rangeCheck(index);
        int byteIndex = index >> 3;
        int bitIndex  = index & 0b111;
        flags[byteIndex] &= (byte) ~(1 << bitIndex);
        return flags.clone();

    }

    public static boolean contains(byte[] flags, int index) {
        rangeCheck(index);
        int byteIndex = index >> 3;
        int bitIndex  = index & 0b111;
        return (flags[byteIndex] & (1 << bitIndex)) != 0;
    }

    public static byte[] empty() {
        return new byte[RECIPE_BYTES];
    }

    private static void rangeCheck(int index) {
        if (index < 0 || index >= MAX_RECIPES) {
            throw new IndexOutOfBoundsException("Recipe index out of range: " + index);
        }
    }

    public static String toBitStringLSBRight(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 8);
        for (int i = bytes.length - 1; i >= 0; i--) {
            for (int bit = 7; bit >= 0; bit--) {
                sb.append(((bytes[i] >> bit) & 1) == 1 ? '1' : '0');
            }
        }
        return sb.toString();
    }
}