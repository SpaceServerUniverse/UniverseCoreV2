package space.yurisi.universecorev2.subplugins.universeeconomy;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.repositories.MoneyRepository;
import space.yurisi.universecorev2.database.repositories.UserRepository;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeeconomy.file.Config;

public class UniverseCoreAPIConnector {

    private UserRepository userRepository;

    private MoneyRepository moneyRepository;


    protected final Long max;
    protected final String unit;

    public UniverseCoreAPIConnector(DatabaseManager databaseManager, Config config){
        setUserRepository(databaseManager.getUserRepository());
        setMoneyRepository(databaseManager.getMoneyRepository());
        this.max = config.getMax();
        this.unit = config.getUnit();
    }

    protected void baseAddMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotAddMoneyException, ParameterException {
        Long user_id;
        Money money_model;
        user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        money_model = moneyRepository.getMoneyFromUserId(user_id);

        if (!baseCanAddMoney(money_model, amount)) {
            throw new CanNotAddMoneyException();
        }

        if(amount <= 0){
            throw new ParameterException("amountが0以下です。");
        }

        Long money = money_model.getMoney();
        money_model.setMoney(money + amount);
        if(reason == null){
            moneyRepository.updateMoney(money_model, amount);
        }
        moneyRepository.updateMoney(money_model, amount, reason);
    }

    protected void baseReduceMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, ParameterException {
        Long user_id;
        Money money_model;
        user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        money_model = moneyRepository.getMoneyFromUserId(user_id);

        if (!baseCanReduceMoney(money_model, amount)) {
            throw new CanNotReduceMoneyException();
        }

        if(amount <= 0){
            throw new ParameterException("amountが0以下です。");
        }

        Long money = money_model.getMoney();
        money_model.setMoney(money - amount);
        Long history_amount = amount * -1;
        if(reason == null){
            moneyRepository.updateMoney(money_model, history_amount);
        }
        moneyRepository.updateMoney(money_model , history_amount, reason);
    }

    protected void baseSetMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, ParameterException {
        Long user_id;
        Money money_model;
        user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        money_model = moneyRepository.getMoneyFromUserId(user_id);

        if(amount <= 0){
            throw new ParameterException("amountが0以下です。");
        }

        Long money = money_model.getMoney();
        Long diff_money = amount - money;
        money_model.setMoney(amount);
        if(reason == null) {
            moneyRepository.updateMoney(money_model, diff_money);
        }
        moneyRepository.updateMoney(money_model, diff_money, reason);
    }

    public Long baseGetMoney(Player player) throws UserNotFoundException, MoneyNotFoundException {
        Long user_id = userRepository.getPrimaryKeyFromUUID(player.getUniqueId());
        Money money_model = moneyRepository.getMoneyFromUserId(user_id);

        return money_model.getMoney();
    }

    public Long baseGetMoneyFromUserName(String name) throws UserNotFoundException, MoneyNotFoundException {
        Long user_id = userRepository.getPrimaryKeyFromPlayerName(name);
        Money money_model = moneyRepository.getMoneyFromUserId(user_id);

        return money_model.getMoney();
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
}
