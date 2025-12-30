package space.yurisi.universecorev2.model.wallet;

import java.util.Optional;

/**
 * 0より大きい金額を表すための値オブジェクト
 */
public record NonZeroAmount(Amount amount) {
    /**
     * 用途に応じて {@link NonZeroAmount#maybe(long)} か {@link NonZeroAmount#must(long)} を代わりに使用してください
     * @see NonZeroAmount#must(long)
     * @see NonZeroAmount#maybe(long)
     */
    public NonZeroAmount {
        if(amount.value() <= 0) {
            throw new IllegalArgumentException("値は0になってはいけません");
        }
    }

    /**
     * 範囲に収まる保証がある場合に{@link NonZeroAmount}を得る
     */
    public static NonZeroAmount must(long value) {
        return new NonZeroAmount(Amount.must(value));
    }

    /**
     * 範囲に収まる保証がないときでも安全に NonZeroAmountを得る
     */
    public static Optional<NonZeroAmount> maybe(long value) {
        return Amount.maybe(value)
                .filter(a -> a.value() > 0)
                .map(NonZeroAmount::new);
    }

    public long value() {
        return amount.value();
    }
}
