package space.yurisi.universecorev2.subplugins.achievement.data;

import org.jetbrains.annotations.NotNull;

public final class AchievementStatus {

    private final boolean isAchieved;
    private final Long goal;
    private final String stage;
    private final String color;

    private AchievementStatus(boolean isAchieved, @NotNull Long goal, @NotNull String stage, @NotNull String color) {
        this.isAchieved = isAchieved;
        this.goal = goal;
        this.stage = stage;
        this.color = color;
    }

    public static AchievementStatus create(boolean isAchieved, @NotNull Long goal, @NotNull String stage, @NotNull String color) {
        return new AchievementStatus(isAchieved, goal, stage, color);
    }

    public boolean isAchieved() {
        return isAchieved;
    }

    public Long getGoal() {
        return goal;
    }

    public String getStage() {
        return stage;
    }

    public String getColor() {
        return color;
    }
}
