package space.yurisi.universecorev2.subplugins.universeeconomy;

import org.bukkit.entity.Player;
import space.yurisi.universecorev2.database.DatabaseManager;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universeeconomy.file.Config;

/**
 * The type Universe economy api.
 */
public class UniverseEconomyAPI extends UniverseCoreAPIConnector{

    private static UniverseEconomyAPI api;

    /**
     * Instantiates a new Universe economy api.
     *
     * @param databaseManager the database manager
     */
    public UniverseEconomyAPI(DatabaseManager databaseManager, Config config) {
        super(databaseManager, config);
        api = this;
    }

    /**
     * 指定したプレイヤーの所持金を追加する
     *
     * @param player プレイヤー
     * @param amount お金の量
     * @throws UserNotFoundException   ユーザーデータが存在しない
     * @throws MoneyNotFoundException  お金データが存在しない
     * @throws CanNotAddMoneyException 桁数が多い
     * @throws ParameterException      マイナスを指定している
     */
    public void addMoney(Player player, Long amount) throws UserNotFoundException, MoneyNotFoundException, CanNotAddMoneyException, ParameterException {
        this.baseAddMoney(player, amount, "原因不明");
    }

    /**
     * 指定したプレイヤーの所持金を理由と一緒に追加する
     *
     * @param player プレイヤー
     * @param amount お金の量
     * @param reason 理由
     * @throws UserNotFoundException   ユーザーデータが存在しない
     * @throws MoneyNotFoundException  お金データが存在しない
     * @throws CanNotAddMoneyException 桁数が多い
     * @throws ParameterException      マイナスを指定している
     */
    public void addMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotAddMoneyException, ParameterException {
        this.baseAddMoney(player, amount, reason);
    }

    /**
     * 指定したプレイヤーの所持金を減らす
     *
     * @param player プレイヤー
     * @param amount お金の量
     * @throws UserNotFoundException   ユーザーデータが存在しない
     * @throws MoneyNotFoundException  お金データが存在しない
     * @throws CanNotReduceMoneyException 桁数が多い
     * @throws ParameterException      マイナスを指定している
     */
    public void reduceMoney(Player player, Long amount) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, ParameterException {
       this.baseReduceMoney(player, amount, null);
    }

    /**
     * 指定したプレイヤーの所持金を理由とともに減らす
     *
     * @param player プレイヤー
     * @param amount お金の量
     * @param reason 理由
     * @throws UserNotFoundException   ユーザーデータが存在しない
     * @throws MoneyNotFoundException  お金データが存在しない
     * @throws CanNotReduceMoneyException 桁数が多い
     * @throws ParameterException      マイナスを指定している
     */
    public void reduceMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, ParameterException {
        this.baseReduceMoney(player, amount, reason);
    }

    /**
     * 指定したプレイヤーの所持金をセットする
     *
     * @param player プレイヤー
     * @param amount お金の量
     * @throws UserNotFoundException  ユーザーデータが存在しない
     * @throws MoneyNotFoundException お金データが存在しない
     * @throws ParameterException     マイナスを指定している
     */
    public void setMoney(Player player, Long amount) throws UserNotFoundException, MoneyNotFoundException, ParameterException {
        this.baseSetMoney(player, amount, null);
    }

    /**
     * 指定したプレイヤーの所持金をセットする
     *
     * @param player プレイヤー
     * @param amount お金の量
     * @param reason 理由
     * @throws UserNotFoundException  ユーザーデータが存在しない
     * @throws MoneyNotFoundException お金データが存在しない
     * @throws ParameterException     マイナスを指定している
     */
    public void setMoney(Player player, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, ParameterException {
        this.baseSetMoney(player, amount, reason);
    }

    /**
     * お金を取得
     *
     * @param player プレイヤー
     * @return 所持金 Long
     * @throws UserNotFoundException  ユーザーデータが存在しない
     * @throws MoneyNotFoundException お金データが存在しない
     */
    public Long getMoney(Player player) throws UserNotFoundException, MoneyNotFoundException {
        return this.baseGetMoney(player);
    }

    /**
     * お金を取得
     *
     * @param name プレイヤー名
     * @return 所持金 Long
     * @throws UserNotFoundException  ユーザーデータが存在しない
     * @throws MoneyNotFoundException お金データが存在しない
     */
    public Long getMoneyFromUserName(String name) throws UserNotFoundException, MoneyNotFoundException {
        return baseGetMoneyFromUserName(name);
    }

    /**
     * お金を送ります
     *
     * @param from   元ユーザー
     * @param to     送られるユーザー
     * @param amount お金の量
     * @throws UserNotFoundException      ユーザーデータが存在しない
     * @throws MoneyNotFoundException     お金データが存在しない
     * @throws CanNotReduceMoneyException 元ユーザーのお金が足りない
     * @throws CanNotAddMoneyException    送られるユーザーの所持金がいっぱい
     * @throws ParameterException         マイナスを指定している
     */
    public void sendMoney(Player from, Player to, Long amount) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, CanNotAddMoneyException, ParameterException {
        reduceMoney(from, amount);
        addMoney(to, amount);
    }

    /**
     * お金を送ります
     *
     * @param from   元ユーザー
     * @param to     送られるユーザー
     * @param amount お金の量
     * @param reason 理由
     * @throws UserNotFoundException      ユーザーデータが存在しない
     * @throws MoneyNotFoundException     お金データが存在しない
     * @throws CanNotReduceMoneyException 元ユーザーのお金が足りない
     * @throws CanNotAddMoneyException    送られるユーザーの所持金がいっぱい
     * @throws ParameterException         マイナスを指定している
     */
    public void sendMoney(Player from, Player to, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, CanNotAddMoneyException, ParameterException {
        reduceMoney(from, amount, reason);
        addMoney(to, amount, reason);
    }

    /**
     * お金を送ります。エラーが出たらもとに戻します。
     *
     * @param from   元ユーザー
     * @param to     送られるユーザー
     * @param amount お金の量
     * @param reason 理由
     * @throws UserNotFoundException      ユーザーデータが存在しない
     * @throws MoneyNotFoundException     お金データが存在しない
     * @throws CanNotReduceMoneyException 元ユーザーのお金が足りない
     * @throws CanNotAddMoneyException    送られるユーザーの所持金がいっぱい
     * @throws ParameterException         マイナスを指定している
     */
    public void sendMoneyTransaction(Player from, Player to, Long amount, String reason) throws UserNotFoundException, MoneyNotFoundException, CanNotReduceMoneyException, CanNotAddMoneyException, ParameterException {

    }

    /**
     * お金を追加できるか確認します
     *
     * @param money  Moneyモデル
     * @param amount お金の量
     * @return boolean
     */
    public boolean canAddMoney(Money money, Long amount) {
        return this.baseCanAddMoney(money, amount);
    }

    /**
     * お金を減らせるか確認します
     *
     * @param money  Moneyモデル
     * @param amount お金の量
     * @return boolean
     */
    public boolean canReduceMoney(Money money, Long amount) {
        return this.baseCanReduceMoney(money, amount);
    }

    /**
     * お金の単位を取得する
     *
     * @return String 単位
     */
    public String getUnit() {
        return this.unit;
    }

    /**
     * インスタンスを取得する
     *
     * @return the instance
     */
    public static UniverseEconomyAPI getInstance() {
        return api;
    }
}
