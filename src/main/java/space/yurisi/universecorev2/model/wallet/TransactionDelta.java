package space.yurisi.universecorev2.model.wallet;

import java.util.Optional;

/**
 * 0より大きい金額を表すための値オブジェクト
 */
public record TransactionDelta(long value) {
    /**
     * 用途に応じて {@link TransactionDelta#maybe(long)} か {@link TransactionDelta#must(long)} を代わりに使用すること
     * @see TransactionDelta#must(long)
     * @see TransactionDelta#maybe(long)
     */
    public TransactionDelta {
        if (value <= 0) {
            throw new IllegalArgumentException("取引量は0以上である必要があります");
        }
    }

    /**
     * 範囲に収まる保証がある場合に{@link TransactionDelta}を得る
     */
    public static TransactionDelta must(long value) {
        return new TransactionDelta(value);
    }

    /**
     * 範囲に収まる保証がないときでも安全に{@link TransactionDelta}を得る
     */
    public static Optional<TransactionDelta> maybe(long value) {
        if (value <= 0) {
            return Optional.empty();
        }

        return Optional.of(new TransactionDelta(value));
    }
}
