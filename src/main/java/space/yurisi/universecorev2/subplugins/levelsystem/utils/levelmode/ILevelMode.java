package space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode;

public interface ILevelMode {

    String getName();

    int getMaxLevel();

    Long getLevelTable(int level);
}
