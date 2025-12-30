package space.yurisi.universecorev2.model.wallet;

public sealed interface WalletResult {
    record Ok(Wallet wallet) implements WalletResult {}
    record Err(String reason) implements WalletResult {}
}