package space.yurisi.universecorev2.model.wallet;

public sealed interface BalanceResult {
    record Ok(Balance balance) implements BalanceResult {}
    record Err() implements BalanceResult {}
}