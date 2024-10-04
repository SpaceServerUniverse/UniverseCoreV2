package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.User;

import java.util.Date;

public class MoneyRepository {

    private final SessionFactory sessionFactory;
    private final MoneyHistoryRepository moneyHistoryRepository;

    /**
     * Instantiates a new Money repository.
     *
     * @param sessionFactory         session factory
     * @param moneyHistoryRepository MoneyHistoryRepository
     */
    public MoneyRepository(SessionFactory sessionFactory, MoneyHistoryRepository moneyHistoryRepository) {
        this.sessionFactory = sessionFactory;
        this.moneyHistoryRepository = moneyHistoryRepository;
    }

    /**
     * ユーザーに基づきお金を作成します。
     *
     * @param user User
     * @return money Money
     */
    public Money createMoney(User user) {
        Long user_id = user.getId();
        Money money = new Money(null, user_id, 1000L, new Date(), new Date());

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            session.persist(money);//save
            session.getTransaction().commit();
            this.moneyHistoryRepository.createMoneyHistory(money, 1000L, "お金データの作成");
        } finally {
            session.close();
        }
        return money;
    }

    /**
     * お金をプライマリーキーから取得します。
     *
     * @param id Long(PrimaryKey)
     * @return Money
     * @throws MoneyNotFoundException お金データが存在しない
     */
    public Money getMoney(Long id) throws MoneyNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Money data = session.get(Money.class, id);
            session.getTransaction().commit();

            if (data == null) {
                throw new MoneyNotFoundException("お金データが存在しませんでした。 ID:" + id);
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * お金をuser_idから取得します。
     *
     * @param user_id Long
     * @return Money
     * @throws MoneyNotFoundException お金データが存在しない
     */
    public Money getMoneyFromUserId(Long user_id) throws MoneyNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Money data = session.createSelectionQuery("from Money where user_id = ?1", Money.class)
                    .setParameter(1, user_id).getSingleResultOrNull();
            session.getTransaction().commit();

            if (data == null) {
                throw new MoneyNotFoundException("お金データが存在しませんでした。 user_id:" + user_id);
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * 指定したプライマリーキーがデータベースに存在するかを返します
     *
     * @param id Long(Primary key)
     * @return boolean
     */
    public boolean existsMoney(Long id) {
        try {
            getMoney(id);
            return true;
        } catch (MoneyNotFoundException e) {
            return false;
        }
    }

    /**
     * 指定したユーザーIDのカラムがデータベースに存在するかを返します
     *
     * @param user_id Long
     * @return boolean
     */
    public boolean existsMoneyFromUserId(Long user_id) {
        try {
            getMoneyFromUserId(user_id);
            return true;
        } catch (MoneyNotFoundException e) {
            return false;
        }
    }

    /**
     * user_idからプライマリーキーのみを返します。
     *
     * @param user_id Long
     * @return Long(PrimaryKey) long
     * @throws MoneyNotFoundException お金データが存在しない
     */
    public Long getPrimaryKeyFromUserId(Long user_id) throws MoneyNotFoundException {
        Money money = this.getMoneyFromUserId(user_id);
        return money.getId();
    }


    /**
     * お金モデルに基づきデータをアップデートします。
     *
     * @param money Money
     */
    public void updateMoney(Money money) {
        Long money_change = 0L;
        String reason = "";
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(money);//update
            session.getTransaction().commit();
            this.moneyHistoryRepository.createMoneyHistory(money, money_change, reason);
        } finally {
            session.close();
        }
    }

    /**
     * お金モデルに基づきデータをアップデートします。
     *
     * @param money  Money
     * @param reason 理由
     */
    public void updateMoney(Money money, String reason) {
        Long money_change = 0L;
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(money);//update
            session.getTransaction().commit();
            this.moneyHistoryRepository.createMoneyHistory(money, money_change, reason);
        } finally {
            session.close();
        }
    }

    /**
     * お金モデルに基づきデータをアップデートします。
     *
     * @param money Money
     */
    public void updateMoney(Money money, Long money_change) {
        String reason = "";
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(money);//update
            session.getTransaction().commit();
            this.moneyHistoryRepository.createMoneyHistory(money, money_change, reason);
        } finally {
            session.close();
        }
    }

    /**
     * お金モデルに基づきデータをアップデートします。
     *
     * @param money        Money
     * @param money_change お金の増減
     * @param reason       理由
     */
    public void updateMoney(Money money, Long money_change, String reason) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(money);//update
            session.getTransaction().commit();
            this.moneyHistoryRepository.createMoneyHistory(money, money_change, reason);
        } finally {
            session.close();
        }
    }

    /**
     * トランザクション付きで2人のプレイヤーのお金を増減します。
     *
     * @param send_money    Money
     * @param receive_money Money
     * @param money_change  お金の増減
     * @param reason        理由
     */
    public Boolean moveMoney(Money send_money, Money receive_money, Long money_change, String reason) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(send_money);
            session.merge(receive_money);
            session.getTransaction().commit();
            this.moneyHistoryRepository.createMoneyHistory(send_money, money_change, reason);
            this.moneyHistoryRepository.createMoneyHistory(receive_money, money_change, reason);

        } catch (Exception ignored) {
            return false;
        } finally {
            session.close();
        }

        return true;
    }

    /**
     * お金モデルに基づきデータを削除します。
     *
     * @param money Money
     */
    public void deleteMoney(Money money) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(money); //delete
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}
