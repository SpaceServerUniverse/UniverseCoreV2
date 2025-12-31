package space.yurisi.universecorev2.repository;

import space.yurisi.universecorev2.model.user.UserId;
import space.yurisi.universecorev2.model.wallet.History;

import java.util.Date;

public interface WalletHistoryRepository {
    void create(History history);
    History[] findLatestWithLimit(UserId userId, Date since, int limit);
    History[] findAfterWithLimit(UserId userId, Date since, int limit);
    History[] findBeforeWithLimit(UserId userId, Date until, int limit);
}
