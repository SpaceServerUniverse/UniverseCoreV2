package space.yurisi.universecorev2.subplugins.chestshop.transaction;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.AtomicAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.Compensation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 原子性 (すべて実行されるか,全く実行されないか) が必要な一連の操作を管理し、実行します。
 * <p>登録された {@link AtomicAction} をFIFOで順次実行し、
 * いずれかのアクションで例外が発生した場合には、それまでに完了したアクションを
 * 逆順(LIFO)で補償処理することで実行の状態に戻し一貫性を保ちます。</p>
 */
public class Transaction {
    private final String id;
    private final Logger logger;
    private final Queue<AtomicAction> actions = new ArrayDeque<>();

    public Transaction(@NotNull Logger logger) {
        this.id = generateTxId();
        this.logger = logger;
    }

    private static final AtomicInteger counter = new AtomicInteger(0);
    private static String generateTxId() {
        String timePart = Long.toString(System.currentTimeMillis(), 36);
        String countPart = Integer.toString(counter.getAndIncrement() & 0xFFF, 36);

        return (timePart + "-" + countPart).toUpperCase();
    }

    public static Transaction create(@NotNull Logger logger) {
        return new Transaction(logger);
    }

    public String getId() {
        return id;
    }

    /**
     * トランザクションで次のアクションを追加します。
     * @param action 追加するアクション
     * @return this
     */
    public Transaction then(AtomicAction action) {
        actions.add(action);
        return this;
    }

    /**
     * トランザクションを実行します。
     * @throws InterruptTransactionException アクションの実行に失敗し、ロールバックが正常に完了した場合 (実行前の状態に戻せた場合)
     * @throws InconsistentTransactionException アクションの実行に失敗し、かつロールバックの実行も失敗した場合 (実行前の状態に戻せなかった場合)
     */
    public void commit() throws InterruptTransactionException, InconsistentTransactionException {
        logger.info("[tx-{}]Transaction started.", id);
        ArrayDeque<Compensation> rollbackStack = new ArrayDeque<>();
        try {
            while (!actions.isEmpty()) {
                rollbackStack.push(actions.poll().execute());
            }
        } catch (InterruptTransactionException e) {
            logger.info("[tx-{}]Transaction was interrupted: {}", id, e.getMessage());
            ArrayList<Exception> rollbackExceptions = new ArrayList<>();
            while (!rollbackStack.isEmpty()) {
                try {
                    rollbackStack.pop().execute();
                } catch (Exception rollbackException) {
                    rollbackExceptions.add(rollbackException);
                }
            }

            // ロールバック時にエラーが出ていたらもう呼び出し側でも何もできない
            if (!rollbackExceptions.isEmpty()) {
                InconsistentTransactionException fatal = new InconsistentTransactionException("Transaction has not been recovered completely.", e);
                for (Exception rollbackException: rollbackExceptions) {
                    fatal.addSuppressed(rollbackException);
                }
                logger.error("[tx-{}]Transaction was incompletely rolled back", id, fatal);
                throw fatal;
            }

            throw e;
        }
        logger.info("[tx-{}]Transaction committed successfully.", id);
    }
}
