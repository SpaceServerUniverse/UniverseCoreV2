package space.yurisi.universecorev2.model.wallet;

import java.util.Optional;

/**
 * 金額を表すための値オブジェクト
 */
public record Amount(long value) {
    public final static long MAX = 1000000000L;

    /**
     * 用途に応じて {@link Amount#maybe(long)} か {@link Amount#must(long)} を代わりに使用してください
     * @see NonZeroAmount#must(long)
     * @see NonZeroAmount#maybe(long)
     */
    public Amount {
        if (isOutOfRange(value)) {
            throw new IllegalArgumentException("値が範囲外です");
        }
    }

    private static boolean isOutOfRange(long value) {
        return (value < 0 || MAX < value);
    }

    public static Amount must(long value) {
        return new Amount(value);
    }

    public static Optional<Amount> maybe(long value) {
        if (isOutOfRange(value)) {
            return Optional.empty();
        }

        return Optional.of(new Amount(value));
    }

    public Optional<Amount> add(Amount other) {
        return Amount.maybe(this.value + other.value);
    }

    public Optional<Amount> subtract(Amount other) {
        return Amount.maybe(this.value - other.value);
    }

    /**
     * 現在の値をもとに{@link NonZeroAmount}への変換を試みる
     * @return 変換後の値
     */
    public Optional<NonZeroAmount> asNonZero() {
        return NonZeroAmount.maybe(this.value);
    }
}
