package space.yurisi.universecorev2.subplugins.universeslot.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * スロットの役と確率を管理するクラス
 * 設定値(1~6)に応じた当選確率を提供する
 */
public class RoleManager {

    /**
     * スロットの役を定義するEnum
     */
    public enum SlotRole {
        YURISI_HEAD("ゆりしの顔", 300),
        MEYASON_HEAD("めやそんの顔", 200),
        DIAMOND("ダイヤモンド", 80),
        BELL("ベル", 50),
        GLOW_BERRIES("グロウベリー", 30),
        SWEET_BERRIES("ベリー", 20),
        COD("タラ", 10),
        GREEN_BUNDLE("緑のバンドル", 5),
        MISS("ハズレ", 0);

        private final String displayName;
        private final int payout;

        SlotRole(String displayName, int payout) {
            this.displayName = displayName;
            this.payout = payout;
        }

        public String getDisplayName() {
            return displayName;
        }

        /**
         * 配当倍率を取得
         * @return 配当倍率
         */
        public int getPayout() {
            return payout;
        }
    }

    // 設定ごとの確率テーブル（確率は小数点以下の精度を保つため100000倍した整数値で管理）
    // 例: 0.020% = 20 (100000分の20)
    private static final Map<Integer, Map<SlotRole, Integer>> PROBABILITY_TABLE = new HashMap<>();

    static {
        // 設定1（機械割85%）
        Map<SlotRole, Integer> setting1 = new HashMap<>();
        setting1.put(SlotRole.YURISI_HEAD, 20);       // 0.020%
        setting1.put(SlotRole.MEYASON_HEAD, 50);      // 0.050%
        setting1.put(SlotRole.DIAMOND, 600);          // 0.60%
        setting1.put(SlotRole.BELL, 1800);            // 1.80%
        setting1.put(SlotRole.GLOW_BERRIES, 5000);    // 5.00%
        setting1.put(SlotRole.SWEET_BERRIES, 7000);   // 7.00%
        setting1.put(SlotRole.COD, 12000);            // 12.00%
        setting1.put(SlotRole.GREEN_BUNDLE, 18000);   // 18.00%
        setting1.put(SlotRole.MISS, 55530);           // 55.53%
        PROBABILITY_TABLE.put(1, setting1);

        // 設定2（機械割90%）
        Map<SlotRole, Integer> setting2 = new HashMap<>();
        setting2.put(SlotRole.YURISI_HEAD, 30);       // 0.030%
        setting2.put(SlotRole.MEYASON_HEAD, 80);      // 0.080%
        setting2.put(SlotRole.DIAMOND, 900);          // 0.90%
        setting2.put(SlotRole.BELL, 2500);            // 2.50%
        setting2.put(SlotRole.GLOW_BERRIES, 6000);    // 6.00%
        setting2.put(SlotRole.SWEET_BERRIES, 8000);   // 8.00%
        setting2.put(SlotRole.COD, 13000);            // 13.00%
        setting2.put(SlotRole.GREEN_BUNDLE, 19000);   // 19.00%
        setting2.put(SlotRole.MISS, 50490);           // 50.49%
        PROBABILITY_TABLE.put(2, setting2);

        // 設定3（機械割95%）
        Map<SlotRole, Integer> setting3 = new HashMap<>();
        setting3.put(SlotRole.YURISI_HEAD, 50);       // 0.050%
        setting3.put(SlotRole.MEYASON_HEAD, 120);     // 0.120%
        setting3.put(SlotRole.DIAMOND, 1200);         // 1.20%
        setting3.put(SlotRole.BELL, 3200);            // 3.20%
        setting3.put(SlotRole.GLOW_BERRIES, 7000);    // 7.00%
        setting3.put(SlotRole.SWEET_BERRIES, 9000);   // 9.00%
        setting3.put(SlotRole.COD, 14000);            // 14.00%
        setting3.put(SlotRole.GREEN_BUNDLE, 20000);   // 20.00%
        setting3.put(SlotRole.MISS, 45410);           // 45.41%
        PROBABILITY_TABLE.put(3, setting3);

        // 設定4（機械割98%）
        Map<SlotRole, Integer> setting4 = new HashMap<>();
        setting4.put(SlotRole.YURISI_HEAD, 80);       // 0.080%
        setting4.put(SlotRole.MEYASON_HEAD, 180);     // 0.180%
        setting4.put(SlotRole.DIAMOND, 1600);         // 1.60%
        setting4.put(SlotRole.BELL, 4000);            // 4.00%
        setting4.put(SlotRole.GLOW_BERRIES, 8000);    // 8.00%
        setting4.put(SlotRole.SWEET_BERRIES, 10000);  // 10.00%
        setting4.put(SlotRole.COD, 15000);            // 15.00%
        setting4.put(SlotRole.GREEN_BUNDLE, 21000);   // 21.00%
        setting4.put(SlotRole.MISS, 40140);           // 40.14%
        PROBABILITY_TABLE.put(4, setting4);

        // 設定5（機械割100%）
        Map<SlotRole, Integer> setting5 = new HashMap<>();
        setting5.put(SlotRole.YURISI_HEAD, 100);      // 0.100%
        setting5.put(SlotRole.MEYASON_HEAD, 220);     // 0.220%
        setting5.put(SlotRole.DIAMOND, 1900);         // 1.90%
        setting5.put(SlotRole.BELL, 4800);            // 4.80%
        setting5.put(SlotRole.GLOW_BERRIES, 9000);    // 9.00%
        setting5.put(SlotRole.SWEET_BERRIES, 11000);  // 11.00%
        setting5.put(SlotRole.COD, 16000);            // 16.00%
        setting5.put(SlotRole.GREEN_BUNDLE, 22000);   // 22.00%
        setting5.put(SlotRole.MISS, 34980);           // 34.98%
        PROBABILITY_TABLE.put(5, setting5);

        // 設定6（機械割103%）
        Map<SlotRole, Integer> setting6 = new HashMap<>();
        setting6.put(SlotRole.YURISI_HEAD, 150);      // 0.150%
        setting6.put(SlotRole.MEYASON_HEAD, 300);     // 0.300%
        setting6.put(SlotRole.DIAMOND, 2500);         // 2.50%
        setting6.put(SlotRole.BELL, 6000);            // 6.00%
        setting6.put(SlotRole.GLOW_BERRIES, 12000);   // 12.00%
        setting6.put(SlotRole.SWEET_BERRIES, 14000);  // 14.00%
        setting6.put(SlotRole.COD, 18000);            // 18.00%
        setting6.put(SlotRole.GREEN_BUNDLE, 24000);   // 24.00%
        setting6.put(SlotRole.MISS, 22050);           // 22.05%
        PROBABILITY_TABLE.put(6, setting6);
    }

    private final Random random;

    public RoleManager() {
        this.random = new Random();
    }

    /**
     * 指定された設定値に基づいて抽選を行い、当選した役を返す
     *
     * @param setting 設定値(1~6)
     * @return 当選した役
     * @throws IllegalArgumentException 設定値が1~6の範囲外の場合
     */
    public SlotRole drawRole(int setting) {
        validateSetting(setting);

        Map<SlotRole, Integer> rawProbabilities = PROBABILITY_TABLE.get(setting);
        int randomValue = random.nextInt(100000);
        int cumulative = 0;

        // 役を順番にチェックし、累積確率がランダム値を超えたらその役を返す
        for (SlotRole role : SlotRole.values()) {
            cumulative += rawProbabilities.get(role);
            if (randomValue < cumulative) {
                return role;
            }
        }

        // ここに到達することは通常ないが、念のためハズレを返す
        return SlotRole.MISS;
    }

    /**
     * 設定値を検証する
     *
     * @param setting 設定値
     * @throws IllegalArgumentException 設定値が1~6の範囲外の場合
     */
    private void validateSetting(int setting) {
        if (setting < 1 || setting > 6) {
            throw new IllegalArgumentException("設定値は1~6の範囲で指定してください: " + setting);
        }
    }
}
