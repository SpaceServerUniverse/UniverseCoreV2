package space.yurisi.universecorev2.subplugins.universeeconomy.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MoneyCache {
    private final Map<UUID, Long> money = new ConcurrentHashMap<>();

    public Long getMoney(UUID uuid) {
        return money.get(uuid);
    }

    public void setMoney(UUID uuid, Long value) {
        if (value == null) {
            money.remove(uuid);
            return;
        }
        money.put(uuid, value);
    }

    public boolean hasMoney(UUID uuid) {
        return money.containsKey(uuid);
    }

    public void removeMoney(UUID uuid) {
        money.remove(uuid);
    }

    public void clear() {
        money.clear();
    }

    public Map<UUID, Long> getAllMoneySnapshot() {
        return new HashMap<>(money);
    }
}
