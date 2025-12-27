package space.yurisi.universecorev2.subplugins.chestshop.transaction;

/**
 * ロールバックに失敗し一貫性が失われた場合に投げられる例外
 * 本来であれば Error であるべきだが、PaperはErrorを投げられても止まらないため
 * Exceptionにして明示的に開発者にエラーハンドリングをさせる
 */
public class InconsistentTransactionException extends Exception {
    public InconsistentTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
