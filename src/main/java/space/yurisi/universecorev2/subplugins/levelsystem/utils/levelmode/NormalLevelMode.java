package space.yurisi.universecorev2.subplugins.levelsystem.utils.levelmode;

public class NormalLevelMode implements ILevelMode {
    @Override
    public String getName() {
        return "ノーマル";
    }

    @Override
    public int getMaxLevel() {
        return 1000;
    }

    @Override
    public Long getLevelTable(int level) {
        // 切り捨て(level x 1.2) x 60
        double levelDiff = level * 1.2;
        long floorLevelDiff = (long) Math.floor(levelDiff);
        return floorLevelDiff * 60;
    }
}
