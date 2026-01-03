package space.yurisi.universecorev2.subplugins.chestshop.transaction.action;

/**
 * 実行された操作を打ち消すための「補償処理」を表すインターフェース
 * @see AtomicAction
 */
@FunctionalInterface
public interface Compensation {
    /**
     * 補償処理（打ち消し操作）を実行する
     * @throws Exception 補償処理自体に失敗した場合
     */
    void execute() throws Exception;
}
