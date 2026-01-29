package space.yurisi.universecorev2.subplugins.autocollect;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * プレイヤーの自動回収機能を管理するクラス．
 * プレイヤーのUUIDを使用して，プレイヤーごとの自動回収機能の状態を管理します．
 */
public class AutoCollectManager {

    /**
     * 各プレイヤーの自動回収状態を保持するマップ．
     * キー: プレイヤーのUUID, 値: 自動回収が有効かどうか
     */
    private final Map<UUID, AutoCollectState> stateMap;

    public AutoCollectManager() {
        this.stateMap = new HashMap<>();
    }

    /**
     * 指定されたプレイヤーの自動回収状態を切り替えます．
     * 切り替え後の状態を返します．
     *
     * @param player プレイヤー
     * @return 切り替え後の自動回収状態
     */
    @NotNull
    public AutoCollectState toggleAutoCollect(@NotNull Player player) {
        UUID playerId = player.getUniqueId();
        AutoCollectState currentState = getStateOrDefault(playerId);
        AutoCollectState newState = currentState.toggle();
        stateMap.put(playerId, newState);
        return newState;
    }

    /**
     * 指定されたプレイヤーの自動回収が有効かどうかを取得します．
     * 未登録の場合はデフォルトでfalseを返します．
     *
     * @param player プレイヤー
     * @return 自動回収が有効かどうか
     */
    public boolean isAutoCollectEnabled(@NotNull Player player) {
        return getStateOrDefault(player.getUniqueId()).isEnabled();
    }

    /**
     * 指定されたプレイヤーの自動回収状態を取得します．
     * 未登録の場合は空のOptionalを返します．
     *
     * @param player プレイヤー
     * @return プレイヤーの自動回収状態
     */
    @NotNull
    public Optional<AutoCollectState> getState(@NotNull Player player) {
        return Optional.ofNullable(stateMap.get(player.getUniqueId()));
    }

    /**
     * 指定されたプレイヤーの自動回収状態を設定します．
     * 既に登録されている場合は上書きされます．
     *
     * @param player プレイヤー
     * @param state プレイヤーの自動回収状態
     */
    public void setState(@NotNull Player player, @NotNull AutoCollectState state) {
        stateMap.put(player.getUniqueId(), state);
    }

    /**
     * 指定されたプレイヤーが登録されているかどうかを確認します．
     * 登録されていない場合はfalseを返します．
     *
     * @param player プレイヤー
     * @return 登録されているかどうか
     */
    public boolean isRegistered(@NotNull Player player) {
        return stateMap.containsKey(player.getUniqueId());
    }

    /**
     * 指定されたプレイヤーを登録します．
     * 既に登録されている場合はfalseを返します．
     *
     * @param player プレイヤー
     * @return 登録に成功したかどうか
     */
    public boolean registerPlayer(@NotNull Player player) {
        UUID uuid = player.getUniqueId();
        if (stateMap.containsKey(uuid)) {
            return false;
        }
        stateMap.put(uuid, AutoCollectState.DISABLED);
        return true;
    }

    /**
     * 指定されたプレイヤーの登録を解除します．
     * 登録されていなかった場合は空のOptionalを返します．
     *
     * @param player プレイヤー
     * @return 解除されたプレイヤーの自動回収状態
     */
    @NotNull
    public Optional<AutoCollectState> unregisterPlayer(@NotNull Player player) {
        return Optional.ofNullable(stateMap.remove(player.getUniqueId()));
    }

    /**
     * 全てのプレイヤーの自動回収状態をクリアします．
     */
    public void clear() {
        stateMap.clear();
    }

    /**
     * 登録されているプレイヤーの数を取得します．
     *
     * @return 登録されているプレイヤーの数
     */
    public int getRegisteredPlayerCount() {
        return stateMap.size();
    }

    /**
     * 自動回収が有効なプレイヤーの数を取得します．
     *
     * @return 自動回収が有効なプレイヤーの数
     */
    public long getEnabledPlayerCount() {
        return stateMap.values().stream()
                .filter(AutoCollectState::isEnabled)
                .count();
    }



    /**
     * 指定されたプレイヤーの自動回収状態を取得します．
     * 未登録の場合はデフォルトでDISABLEDを返します．
     *
     * @param playerId　プレイヤーのUUID
     * @return プレイヤーの自動回収状態
     */
    @NotNull
    private AutoCollectState getStateOrDefault(@NotNull UUID playerId) {
        return stateMap.getOrDefault(playerId, AutoCollectState.DISABLED);
    }

}
