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
import java.util.UUID;

public class BirthdayCardRepository {
    private final SessionFactory sessionFactory;

    public BirthdayCardRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * バースデーデータを作成します
     *
     * @param uuidMC UUID
     * @param month 月
     * @param day 日
     * @return BirthdayData
     */
    public BirthdayData createBirthdayData(UUID uuidMC, int month, int day) {
        String uuid = uuidMC.toString();
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
     * @param uuidMC
     * @return BirthdayData
     * @throws BirthdayDataNotFoundException
     */
    public BirthdayData getBirthdayData(UUID uuidMC) throws BirthdayDataNotFoundException {
        String uuid = uuidMC.toString();
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            BirthdayData data = session.createQuery("FROM BirthdayData where uuid = :uuid", BirthdayData.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
            session.getTransaction().commit();
            return data;
        } catch (NoResultException error) {
            throw new BirthdayDataNotFoundException("Birthday data not found");
        } finally {
            session.close();
        }
    }

    /**
     * 特定のUUIDのデータが存在するかを確認します
     * @param uuidMC PlayerのUUID
     * @return 存在するか
     */
    public boolean existsBirthdayData(UUID uuidMC) {
        String uuid = uuidMC.toString();
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            Long count = session.createQuery(
                            "SELECT COUNT(b) FROM BirthdayData b WHERE b.uuid = :uuid",
                            Long.class
                    )
                    .setParameter("uuid", uuid)
                    .getSingleResult();

            session.getTransaction().commit();
            return count != null && count > 0;
        } finally {
            session.close();
        }
    }

    /**
     * すべてのバースデーデータを取得します
     *
     * @return List
     */
    public List<BirthdayData> getAllBirthdayData() {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayData> data = session.createSelectionQuery("FROM BirthdayData", BirthdayData.class).getResultList();
            session.getTransaction().commit();
            return data;
        } finally {
            session.close();
        }

    }

    public List<BirthdayData> getAllToPaginate(int offset, int limit) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<BirthdayData> data = session.createSelectionQuery("FROM BirthdayData", BirthdayData.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();

            session.getTransaction().commit();
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
        try {
            session.beginTransaction();
            session.merge(birthdayData);
            session.getTransaction().commit();
        } finally {
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
    public List<BirthdayData> getBirthdayDataByMonthDay(MonthDay monthDay) {
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
    public List<BirthdayMessages> getBirthdayMessages(Long birthdayDataId) {
        Session session = this.sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();
            List<BirthdayMessages> birthdayMessagesList = session.createQuery("FROM BirthdayMessages WHERE birthdayDataId = :birthdayDataId", BirthdayMessages.class)
                    .setParameter("birthdayDataId", birthdayDataId)
                    .getResultList();
            session.getTransaction().commit();
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
        } finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを削除します
     *
     * @param birthdayMessages
     */
    public void deleteBirthdayMessage(BirthdayMessages birthdayMessages) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(birthdayMessages);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /***
     * ガチャチケを受け取るかどうか
     *
     */
    public boolean canReceiveGachaTicket(BirthdayMessages birthdayMessages) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List birthdayMessagesList = session.createQuery("FROM BirthdayMessages WHERE birthdayDataId = :birthdayDataId AND uuid = :playerUuid AND receivedGachaTicket = true", BirthdayMessages.class)
                    .setParameter("birthdayDataId", birthdayMessages.getBirthdayDataId())
                    .setParameter("playerUuid", birthdayMessages.getUuid())
                    .getResultList();
            return birthdayMessagesList.isEmpty();
        } finally {
            session.close();
        }
    }

    /**
     * バースデーメッセージを更新します
     *
     * @param birthdayMessages
     */
    public void updateBirthdayMessage(BirthdayMessages birthdayMessages) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(birthdayMessages);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

}
