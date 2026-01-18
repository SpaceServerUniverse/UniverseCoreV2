package space.yurisi.universecorev2.subplugins.universeeconomy.cache;

import space.yurisi.universecorev2.database.models.MoneyHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MoneyHistoryCache {
    private final Map<Long, List<MoneyHistory>> historyByUserId = new ConcurrentHashMap<>();

    public List<MoneyHistory> getByUserId(Long userId) {
        List<MoneyHistory> cached = historyByUserId.get(userId);
        if (cached == null) {
            return null;
        }
        return new ArrayList<>(cached);
    }

    public void setByUserId(Long userId, List<MoneyHistory> history) {
        if (history == null) {
            historyByUserId.remove(userId);
            return;
        }
        historyByUserId.put(userId, new ArrayList<>(history));
    }

    public void invalidateUserId(Long userId) {
        historyByUserId.remove(userId);
    }

    public void clear() {
        historyByUserId.clear();
    }
}
