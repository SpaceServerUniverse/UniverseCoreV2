package space.yurisi.universecorev2.subplugins.autocollect;

import org.jetbrains.annotations.NotNull;

public enum AutoCollectState {

    ENABLED(true),
    DISABLED(false)
    ;

    private final boolean enabled;

    AutoCollectState(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 状態が有効かどうかを返す
     *
     * @return 有効な場合はtrue、無効な場合はfalse
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 状態を切り替える
     *
     * @return 切り替え後の状態
     */
    @NotNull
    public AutoCollectState toggle() {
        return enabled ? DISABLED : ENABLED;
    }

    @NotNull
    public static AutoCollectState fromBoolean(boolean enabled) {
        return enabled ? ENABLED : DISABLED;
    }

    @Override
    public String toString() {
        return enabled ? "ENABLED" : "DISABLED";
    }

}
