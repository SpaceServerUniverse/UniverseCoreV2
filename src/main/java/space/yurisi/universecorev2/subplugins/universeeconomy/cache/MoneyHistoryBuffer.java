package space.yurisi.universecorev2.subplugins.universeeconomy.cache;

import space.yurisi.universecorev2.database.models.MoneyHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MoneyHistoryBuffer {
    private final Map<Long, List<MoneyHistory>> pendingByUserId = new ConcurrentHashMap<>();

    public void add(Long userId, MoneyHistory history) {
        pendingByUserId.computeIfAbsent(userId, key -> Collections.synchronizedList(new ArrayList<>())).add(history);
    }

    public List<MoneyHistory> getByUserId(Long userId) {
        List<MoneyHistory> cached = pendingByUserId.get(userId);
        if (cached == null) {
            return null;
        }
        return new ArrayList<>(cached);
    }

    public List<MoneyHistory> drainByUserId(Long userId) {
        List<MoneyHistory> cached = pendingByUserId.remove(userId);
        if (cached == null) {
            return null;
        }
        return new ArrayList<>(cached);
    }

    public Map<Long, List<MoneyHistory>> drainAll() {
        Map<Long, List<MoneyHistory>> snapshot = new ConcurrentHashMap<>();
        for (Map.Entry<Long, List<MoneyHistory>> entry : pendingByUserId.entrySet()) {
            snapshot.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        pendingByUserId.clear();
        return snapshot;
    }

    public void clear() {
        pendingByUserId.clear();
    }
}
