package space.yurisi.universecorev2.subplugins.universeeconomy;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.MoneyHistory;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.repositories.MoneyHistoryRepository;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.cache.MoneyCache;
import space.yurisi.universecorev2.subplugins.universeeconomy.cache.MoneyHistoryBuffer;
import space.yurisi.universecorev2.subplugins.universeeconomy.cache.MoneyHistoryCache;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeeconomy.file.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UniverseCoreAPIConnector {

    private UserRepository userRepository;

    private MoneyRepository moneyRepository;

    private MoneyHistoryRepository moneyHistoryRepository;

    private final MoneyCache moneyCache;
    private final MoneyHistoryCache moneyHistoryCache;
    private final MoneyHistoryBuffer moneyHistoryBuffer;
    private final Set<UUID> dirtyUsers;

    protected final Long max;
    protected final String unit;

    public UniverseCoreAPIConnector(DatabaseManager databaseManager, Config config){
        setUserRepository(databaseManager.getUserRepository());
        setMoneyRepository(databaseManager.getMoneyRepository());
        setMoneyHistoryRepository(databaseManager.getMoneyHistoryRepository());
        this.moneyCache = new MoneyCache();
        this.moneyHistoryCache = new MoneyHistoryCache();
        this.moneyHistoryBuffer = new MoneyHistoryBuffer();
        this.dirtyUsers = ConcurrentHashMap.newKeySet();
        this.max = config.getMax();
        this.unit = config.getUnit();
    }

    protected void baseAddMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotAddMoneyException, ParameterException {
        UUID uuid = player.getUniqueId();
        Long userId = userRepository.getPrimaryKeyFromUUID(uuid);
        Long currentMoney = moneyCache.getMoney(uuid);
        if (currentMoney == null) {
            Money moneyModel = moneyRepository.getMoneyFromUserId(userId);
            currentMoney = moneyModel.getMoney();
            moneyCache.setMoney(uuid, currentMoney);
        }

        Money defaultMoneyModel = new Money(null, userId, currentMoney, null, null);
        if (!baseCanAddMoney(defaultMoneyModel, amount)) {
            throw new CanNotAddMoneyException();
        }

        if(amount <= 0){
            throw new ParameterException("amountが0以下です。");
        }

        Long newMoney = currentMoney + amount;
        MoneyHistory moneyHistory = new MoneyHistory(null, userId, amount, newMoney, reason == null ? "" : reason, new Date(), new Date());
        moneyCache.setMoney(uuid, newMoney);
        moneyHistoryBuffer.add(userId, moneyHistory);
        dirtyUsers.add(uuid);
    }

    protected void baseReduceMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, ParameterException {
        UUID uuid = player.getUniqueId();
        Long userId = userRepository.getPrimaryKeyFromUUID(uuid);
        Long currentMoney = moneyCache.getMoney(uuid);
        if (currentMoney == null) {
            Money moneyModel = moneyRepository.getMoneyFromUserId(userId);
            currentMoney = moneyModel.getMoney();
            moneyCache.setMoney(uuid, currentMoney);
        }

        if (!baseCanReduceMoney(new Money(null, userId, currentMoney, null, null), amount)) {
            throw new CanNotReduceMoneyException();
        }

        if(amount <= 0){
            throw new ParameterException("amountが0以下です。");
        }

        Long newMoney = currentMoney - amount;
        Long historyAmount = amount * -1;
        moneyCache.setMoney(uuid, newMoney);
        moneyHistoryBuffer.add(userId, new MoneyHistory(null, userId, historyAmount, newMoney, reason == null ? "" : reason, new Date(), new Date()));
        dirtyUsers.add(uuid);
    }

    protected void baseSetMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, ParameterException {
        UUID uuid = player.getUniqueId();
        Long userId = userRepository.getPrimaryKeyFromUUID(uuid);
        Long currentMoney = moneyCache.getMoney(uuid);
        if (currentMoney == null) {
            Money moneyModel = moneyRepository.getMoneyFromUserId(userId);
            currentMoney = moneyModel.getMoney();
            moneyCache.setMoney(uuid, currentMoney);
        }

        if(amount <= 0){
            throw new ParameterException("amountが0以下です。");
        }

        Long diffMoney = amount - currentMoney;
        moneyCache.setMoney(uuid, amount);
        moneyHistoryBuffer.add(userId, new MoneyHistory(null, userId, diffMoney, amount, reason == null ? "" : reason, new Date(), new Date()));
        dirtyUsers.add(uuid);
    }

    public Long baseGetMoney(Player player) throws UserNotFoundException, MoneyNotFoundException {
        UUID uuid = player.getUniqueId();
        Long cachedMoney = moneyCache.getMoney(uuid);
        if (cachedMoney != null) {
            return cachedMoney;
        }

        Long user_id = userRepository.getPrimaryKeyFromUUID(uuid);
        Money money_model = moneyRepository.getMoneyFromUserId(user_id);

        Long money = money_model.getMoney();
        moneyCache.setMoney(uuid, money);
        return money;
    }

    public Long baseGetMoneyFromUserName(String name) throws UserNotFoundException, MoneyNotFoundException {
        User user = userRepository.getUserFromPlayerName(name);
        UUID uuid = user.getFormattedUUID();
        Long cachedMoney = moneyCache.getMoney(uuid);
        if (cachedMoney != null) {
            return cachedMoney;
        }

        Money money_model = moneyRepository.getMoneyFromUserId(user.getId());

        Long money = money_model.getMoney();
        moneyCache.setMoney(uuid, money);
        return money;
    }

    public List<MoneyHistory> baseGetMoneyHistory(Player player) throws UserNotFoundException, MoneyNotFoundException {
        Long userId = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        List<MoneyHistory> cached = moneyHistoryCache.getByUserId(userId);
        if (cached == null) {
            try {
                cached = moneyHistoryRepository.getMoneyHistoryFromUserId(userId);
                moneyHistoryCache.setByUserId(userId, cached);
            } catch (MoneyNotFoundException e) {
                cached = new ArrayList<>();
            }
        }

        List<MoneyHistory> pending = moneyHistoryBuffer.getByUserId(userId);
        List<MoneyHistory> merged = new ArrayList<>(cached);
        if (pending != null) {
            merged.addAll(pending);
        }

        if (merged.isEmpty()) {
            throw new MoneyNotFoundException("お金データが存在しませんでした。 user_id:" + userId);
        }

        return merged;
    }

    public List<MoneyHistory> baseGetMoneyHistoryFromUserName(String name) throws UserNotFoundException, MoneyNotFoundException {
        Long userId = userRepository.getPrimaryKeyFromPlayerName(name);
        List<MoneyHistory> cached = moneyHistoryCache.getByUserId(userId);
        if (cached == null) {
            try {
                cached = moneyHistoryRepository.getMoneyHistoryFromUserId(userId);
                moneyHistoryCache.setByUserId(userId, cached);
            } catch (MoneyNotFoundException e) {
                cached = new ArrayList<>();
            }
        }

        List<MoneyHistory> pending = moneyHistoryBuffer.getByUserId(userId);
        List<MoneyHistory> merged = new ArrayList<>(cached);
        if (pending != null) {
            merged.addAll(pending);
        }

        if (merged.isEmpty()) {
            throw new MoneyNotFoundException("お金データが存在しませんでした。 user_id:" + userId);
        }

        return merged;
    }

    public void baseFlushPlayer(UUID uuid) throws UserNotFoundException, MoneyNotFoundException {
        Long userId = userRepository.getPrimaryKeyFromUUID(uuid);
        Long cachedMoney = moneyCache.getMoney(uuid);
        if (cachedMoney != null) {
            Money moneyModel = moneyRepository.getMoneyFromUserId(userId);
            moneyModel.setMoney(cachedMoney);
            moneyRepository.updateMoneyWithoutHistory(moneyModel);
        }

        List<MoneyHistory> pending = moneyHistoryBuffer.drainByUserId(userId);
        if (pending != null) {
            for (MoneyHistory history : pending) {
                moneyHistoryRepository.createMoneyHistory(history);
            }
        }

        moneyHistoryCache.invalidateUserId(userId);
        dirtyUsers.remove(uuid);
        moneyCache.removeMoney(uuid);
    }

    public void baseFlushAll() {
        for (UUID uuid : Set.copyOf(dirtyUsers)) {
            try {
                baseFlushPlayer(uuid);
            } catch (UserNotFoundException | MoneyNotFoundException ignored) {
            }
        }
        moneyHistoryBuffer.clear();
    }

    public boolean baseCanAddMoney(Money money, Long amount) {
        return money.getMoney() + amount <= 1000000000L;
    }

    public boolean baseCanReduceMoney(Money money, Long amount) {
        return money.getMoney() - amount >= 0;
    }

    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public void setMoneyRepository(MoneyRepository moneyRepository){
        this.moneyRepository = moneyRepository;
    }

    public void setMoneyHistoryRepository(MoneyHistoryRepository moneyHistoryRepository){
        this.moneyHistoryRepository = moneyHistoryRepository;
    }
}
