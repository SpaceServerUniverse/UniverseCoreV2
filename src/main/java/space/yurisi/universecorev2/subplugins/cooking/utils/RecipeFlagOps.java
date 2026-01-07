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

    public static BitSet fromBytes(byte[] bytes) {
        return BitSet.valueOf(bytes);
    }

    public static BitSet empty() {
        return new BitSet();
    }
}
