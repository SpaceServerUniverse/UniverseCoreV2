package space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode;

public enum LevelModes {
    NORMAL,
    ADVANCE,
    EXPERT;

    public static LevelModes fromInteger(int x) {
        return switch (x) {
            case 0 -> NORMAL;
            case 1 -> ADVANCE;
            case 2 -> EXPERT;
            default -> null;
        };
    }
}
