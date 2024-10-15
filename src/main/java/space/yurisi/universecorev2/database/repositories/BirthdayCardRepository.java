package space.yurisi.universecorev2.database.repositories;

import jakarta.persistence.NoResultException;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.BirthdayData;
import space.yurisi.universecorev2.database.models.BirthdayMessages;
import space.yurisi.universecorev2.exception.BirthdayDataNotFoundException;

import java.time.Month;
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
        BirthdayData birthdayData = new BirthdayData(null, uuid, month, day, false);

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
     * バースデーをuuidから取得します
     *
     * @param uuid
     * @return
     * @throws BirthdayDataNotFoundException
     */
    public BirthdayData getBirthdayData(String uuid) throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            BirthdayData data = session.createQuery("FROM BirthdayData where uuid = :uuid", BirthdayData.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
            session.getTransaction().commit();
            return data;
        }catch (NoResultException error){
            throw new BirthdayDataNotFoundException("Birthday data not found");
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
            if (data == null || data.isEmpty()) {
                throw new BirthdayDataNotFoundException("Birthday data not found");
            }
            return data;
        } finally {
            session.close();
        }

    }

    /**
     * バースデーデータを更新します
     *
     * @param birthdayData
     */
    public void updateBirthdayData(BirthdayData birthdayData) {
        Session session = this.sessionFactory.getCurrentSession();
        try{
            session.beginTransaction();
            session.merge(birthdayData);
            session.getTransaction().commit();
        }finally {
            session.close();
        }
    }

    /**
     * バースデーを削除します
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
     * @return 指定された月日が存在しない場合は空のリスト、存在する場合は該当するバースデーデータのリスト
     */
    public List<BirthdayData> getBirthdayDataByMonthDay(MonthDay monthDay){
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayData> data = session.createQuery("FROM BirthdayData WHERE month = :month AND day = :day", BirthdayData.class)
                    .setParameter("month", monthDay.getMonthValue())
                    .setParameter("day", monthDay.getDayOfMonth())
                    .getResultList();
            session.getTransaction().commit();

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * Monthからバースデーデータを複数取得します
     *
     * @param month
     * @return 指定された月が存在しない場合は空のリスト、存在する場合は該当するバースデーデータのリスト
     */
    public List<BirthdayData> getBirthdayDataByMonth(Month month) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayData> data = session.createQuery("FROM BirthdayData WHERE month = :month", BirthdayData.class)
                    .setParameter("month", month.getValue())
                    .getResultList();
            session.getTransaction().commit();

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを取得
     *
     * @param birthdayDataId
     * @return
     */
    public List<BirthdayMessages> getBirthdayMessages(Long birthdayDataId) throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            List<BirthdayMessages> birthdayMessagesList = session.createQuery("FROM BirthdayMessages WHERE birthdayDataId = :birthdayDataId", BirthdayMessages.class)
                    .setParameter("birthdayDataId", birthdayDataId)
                    .getResultList();
            session.getTransaction().commit();
            if (birthdayMessagesList.isEmpty()) {
                throw new BirthdayDataNotFoundException("Birthday messages not found for " + birthdayDataId);
            }
            return birthdayMessagesList;
        } finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを作成します
     *
     * @param birthdayDataId
     * @param player
     * @param message
     * @return
     */
    public BirthdayMessages createBirthdayMessage(Long birthdayDataId, Player player, String message) {
        String uuid = player.getUniqueId().toString();

        BirthdayMessages birthdayMessages = new BirthdayMessages(birthdayDataId, uuid, message);

        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
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

    /**
     * プレイヤーuuidからバースデーメッセージを複数取得
     *
     * @param uuid
     * @return
     * @throws BirthdayDataNotFoundException
     */
    public List<BirthdayMessages> getBirthdayMessagesByUuid(String uuid) throws BirthdayDataNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayMessages> birthdayMessagesList = session.createQuery("FROM BirthdayMessages WHERE uuid = :playerUuid", BirthdayMessages.class)
                    .setParameter("playerUuid", uuid)
                    .getResultList();
            session.getTransaction().commit();
            if (birthdayMessagesList.isEmpty()) {
                throw new BirthdayDataNotFoundException("Birthday messages not found for " + uuid);
            }
            return birthdayMessagesList;
        }finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを削除します
     *
     * @param birthdayMessages
     */
    public void deleteBirthdayMessage(BirthdayMessages birthdayMessages){
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(birthdayMessages);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

}
