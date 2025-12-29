package space.yurisi.universecorev2.subplugins.universeslot.manager;

import org.bukkit.Location;

import java.util.HashMap;

public class SlotStatusManager {

    private HashMap<Location, Integer> slotUseStatus = new HashMap<>();

    // 使用状態フラグ
    public static final int IN_USE = 1;           // 0001 (スロット使用中、ビットが立っていなければ未使用)

    // レーン回転状態フラグ
    public static final int LANE1_SPINNING = 1 << 1;  // 0010 (レーン1回転中)
    public static final int LANE2_SPINNING = 1 << 2;  // 0100 (レーン2回転中)
    public static final int LANE3_SPINNING = 1 << 3;  // 1000 (レーン3回転中)

    /**
     * 指定された場所のスロット状態を設定
     * @param location スロットの場所
     * @param status 状態フラグ
     */
    public void setStatus(Location location, int status) {
        slotUseStatus.put(location, status);
    }

    /**
     * 指定された場所のスロット状態にフラグを追加
     * @param location スロットの場所
     * @param flag 追加するフラグ
     */
    public void addFlag(Location location, int flag) {
        int currentStatus = slotUseStatus.getOrDefault(location, 0);
        slotUseStatus.put(location, currentStatus | flag);
    }

    /**
     * 指定された場所のスロット状態からフラグを削除
     * @param location スロットの場所
     * @param flag 削除するフラグ
     */
    public void removeFlag(Location location, int flag) {
        int currentStatus = slotUseStatus.getOrDefault(location, 0);
        slotUseStatus.put(location, currentStatus & ~flag);
    }

    /**
     * 指定された場所のスロット状態を取得
     * @param location スロットの場所
     * @return 状態フラグ
     */
    public int getStatus(Location location) {
        return slotUseStatus.getOrDefault(location, 0);
    }

    /**
     * 指定されたフラグが設定されているか確認
     * @param location スロットの場所
     * @param flag 確認するフラグ
     * @return フラグが設定されている場合true
     */
    public boolean hasFlag(Location location, int flag) {
        int status = slotUseStatus.getOrDefault(location, 0);
        return (status & flag) != 0;
    }

    /**
     * スロットが使用中か確認
     * @param location スロットの場所
     * @return 使用中の場合true
     */
    public boolean isInUse(Location location) {
        return hasFlag(location, IN_USE);
    }

    /**
     * 指定されたレーンが回転中か確認
     * @param location スロットの場所
     * @param laneNumber レーン番号 (1, 2, 3)
     * @return 回転中の場合true
     */
    public boolean isLaneSpinning(Location location, int laneNumber) {
        int flag = switch (laneNumber) {
            case 1 -> LANE1_SPINNING;
            case 2 -> LANE2_SPINNING;
            case 3 -> LANE3_SPINNING;
            default -> throw new IllegalArgumentException("レーン番号は1-3の範囲で指定してください: " + laneNumber);
        };
        return hasFlag(location, flag);
    }

    /**
     * 回転中のレーン数を取得
     * @param location スロットの場所
     * @return 回転中のレーン数
     */
    public int getSpinningLaneCount(Location location) {
        int count = 0;
        if (hasFlag(location, LANE1_SPINNING)) count++;
        if (hasFlag(location, LANE2_SPINNING)) count++;
        if (hasFlag(location, LANE3_SPINNING)) count++;
        return count;
    }

    /**
     * スロットの状態をクリア
     * @param location スロットの場所
     */
    public void clearStatus(Location location) {
        slotUseStatus.remove(location);
    }

}
