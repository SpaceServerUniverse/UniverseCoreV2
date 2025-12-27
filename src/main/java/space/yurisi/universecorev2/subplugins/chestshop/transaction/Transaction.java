package space.yurisi.universecorev2.subplugins.chestshop.transaction;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.MDC;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.AtomicRollbackableAction;
import space.yurisi.universecorev2.subplugins.chestshop.transaction.action.RollbackFunc;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性のある一連の操作を表します
 */
public class Transaction {
    private final String id;
    private final Logger logger;
    private final Queue<AtomicRollbackableAction> actions = new ArrayDeque<>();

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

    public Transaction then(AtomicRollbackableAction action) {
        actions.add(action);
        return this;
    }

    public void commit() throws InterruptTransactionException {
        logger.info("[tx-{}]Transaction started.", id);
        ArrayDeque<RollbackFunc> rollbackStack = new ArrayDeque<>();
        try {
            while (!actions.isEmpty()) {
                rollbackStack.push(actions.poll().execute());
            }
        } catch (InterruptTransactionException e) {
            while (!rollbackStack.isEmpty()) {
                try {
                    rollbackStack.pop().execute();
                } catch (Exception rollbackException) {
                    e.markAsCritical();
                    e.addSuppressed(rollbackException);
                    logger.error("[tx-{}]Critical error occurred in rollback", id, rollbackException);
                }
            }

            throw e;
        }
        logger.info("[tx-{}]Transaction committed successfully.", id);
    }
}
