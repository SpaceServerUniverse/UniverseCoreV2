package space.yurisi.universecorev2.model.wallet;

import java.util.Optional;

/**
 * 金額を表すための値オブジェクト
 */
public record Balance(long value) {
    public final static long MAX = 1_000_000_000L;

    /**
     * 用途に応じて {@link Balance#maybe(long)} か {@link Balance#must(long)} を代わりに使用してください
     * @see Balance#must(long)
     * @see Balance#maybe(long)
     */
    public Balance {
        if (isOutOfRange(value)) {
            throw new IllegalArgumentException("値が範囲外です");
        }
    }

    private static boolean isOutOfRange(long value) {
        return (value < 0 || MAX < value);
    }

    public static Balance must(long value) {
        return new Balance(value);
    }

    public static Optional<Balance> maybe(long value) {
        if (isOutOfRange(value)) {
            return Optional.empty();
        }

        return Optional.of(new Balance(value));
    }

    public Optional<Balance> add(TransactionDelta other) {
        return Balance.maybe(this.value + other.value());
    }

    public Optional<Balance> subtract(TransactionDelta other) {
        return Balance.maybe(this.value - other.value());
    }
}
