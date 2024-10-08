package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.BirthdayMessages;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;

import java.time.MonthDay;
import java.util.List;

public class BirthdayCardRepository {
    private final SessionFactory sessionFactory;

    public BirthdayCardRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * バースデーデータを作成します
     *
     * @param player   Player
     * @param monthDay MonthDay
     * @return
     */
    public BirthdayData createBirthdayData(Player player, MonthDay monthDay) {
        String uuid = player.getUniqueId().toString();
        int month = monthDay.getMonthValue();
        int day = monthDay.getDayOfMonth();
        BirthdayData birthdayData = new BirthdayData(null, uuid, month, day);

        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(birthdayData);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return birthdayData;
    }

    public BirthdayData getBirthdayData(Long id) throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            BirthdayData data = session.get(BirthdayData.class, id);
            session.getTransaction().commit();
            if (data == null) {
                throw new BirthdayDataNotFoundException("Birthday data not found");
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * すべてのバースデーデータを取得します
     *
     * @return
     * @throws BirthdayDataNotFoundException
     */
    public List<BirthdayData> getAllBirthdayData() throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayData> data = session.createSelectionQuery("FROM BirthdayData", BirthdayData.class).getResultList();
            session.getTransaction().commit();
            if (data == null) {
                throw new BirthdayDataNotFoundException("Birthday data not found");
            }
            return data;
        } finally {
            session.close();
        }

    }

    /**
     * チェストショップデータを削除します
     *
     * @param birthdayData
     */
    public void deleteBirthdayData(BirthdayData birthdayData) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(birthdayData);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * MonthDayからバースデーデータを複数取得します
     *
     * @param monthDay
     * @return
     * @throws BirthdayDataNotFoundException
     */
    public List<BirthdayData> getBirthdayDataByMonthDay(MonthDay monthDay) throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayData> data = session.createQuery("FROM BirthdayData WHERE month = :month AND day = :day", BirthdayData.class)
                    .setParameter("month", monthDay.getMonthValue())
                    .setParameter("day", monthDay.getDayOfMonth())
                    .getResultList();
            session.getTransaction().commit();

            if (data.isEmpty()) {
                throw new BirthdayDataNotFoundException("Birthday data not found for " + monthDay);
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを取得
     *
     * @param birthdayData
     * @return
     */
    public List<BirthdayMessages> getBirthdayMessages(BirthdayData birthdayData) throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            List<BirthdayMessages> birthdayMessagesList = session.createQuery("FROM BirthdayMessages WHERE birthdayData = :birthdayData", BirthdayMessages.class)
                    .setParameter("birthdayData", birthdayData)
                    .getResultList();
            session.getTransaction().commit();
            if (birthdayMessagesList.isEmpty()) {
                throw new BirthdayDataNotFoundException("Birthday messages not found for " + birthdayData);
            }
            return birthdayMessagesList;
        } finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを作成します
     *
     * @param birthdayData
     * @param player
     * @param message
     * @return
     */
    public BirthdayMessages createBirthdayMessage(BirthdayData birthdayData, Player player, String message) {
        String uuid = player.getUniqueId().toString();

        BirthdayMessages birthdayMessages = new BirthdayMessages(birthdayData, uuid, message);

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            birthdayData.getBirthdayMessages().add(birthdayMessages);
            session.persist(birthdayMessages);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return birthdayMessages;
    }
}
