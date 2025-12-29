package space.yurisi.universecorev2.subplugins.cooking.utils;

import java.util.BitSet;

public final class RecipeFlagOps {

    private RecipeFlagOps() {}

    public static void add(BitSet bits, int index) {
        bits.set(index);
    }

    public static void remove(BitSet bits, int index) {
        bits.clear(index);
    }

    public static boolean contains(BitSet bits, int index) {
        return bits.get(index);
    }

    public static byte[] toBytes(BitSet bits, int byteSize) {
        byte[] raw = bits.toByteArray();
        if (raw.length == byteSize) {
            return raw;
        }
        byte[] fixed = new byte[byteSize];
        System.arraycopy(raw, 0, fixed, 0, Math.min(raw.length, byteSize));
        return fixed;
    }

    public static BitSet fromBytes(byte[] bytes) {
        return BitSet.valueOf(bytes);
    }

    public static BitSet empty() {
        return new BitSet();
    }
}
