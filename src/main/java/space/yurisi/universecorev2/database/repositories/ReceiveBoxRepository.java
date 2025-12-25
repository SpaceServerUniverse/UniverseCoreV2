package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.database.models.ReceiveBox;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.ReceiveBoxNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ReceiveBoxRepository {

    private final SessionFactory sessionFactory;

    public ReceiveBoxRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 受取アイテムを作ります
     *
     * @return ReceiveBox
     */
    public ReceiveBox createReceiveBox(ReceiveBox receiveBox) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(receiveBox);
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return receiveBox;
    }

    /**
     * 受取アイテムをプライマリーキーから検索します
     * @param id
     * @return ReceiveBox
     * @throws ReceiveBoxNotFoundException
     */
    public ReceiveBox getItemFromId(Long id) throws ReceiveBoxNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            ReceiveBox data = session.createSelectionQuery("from ReceiveBox where is_received = 0 and id = :id", ReceiveBox.class)
                    .setParameter("id", id)
                    .getSingleResult();
            session.getTransaction().commit();
            if (data == null) {
                throw new ReceiveBoxNotFoundException("受取ボックスにアイテムが存在しませんでした。id: " + id);
            }
            return data;
        } finally {
            session.close();
        }
    }


    /**
     * 受取アイテムを全て取得します
     *
     * @return Market
     */
    public List<ReceiveBox> getReceiveBoxes() {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<ReceiveBox> receiveBoxes = session.createSelectionQuery("from ReceiveBox where is_received != :is_received", ReceiveBox.class)
                    .setParameter("is_received", true)
                    .getResultList();
            session.getTransaction().commit();
            return receiveBoxes;
        } finally {
            session.close();
        }
    }

    /**
     * 受取アイテムをプレイヤーから検索します
     * @param player
     * @return List
     */
    public List<ReceiveBox> getReceiveBoxesFromPlayer(Player player) {
        Session session = this.sessionFactory.getCurrentSession();
        UUID uuid = player.getUniqueId();
        Date now = new Date();
        try {
            session.beginTransaction();
            List<ReceiveBox> receiveBoxes = session.createQuery(
                            "from ReceiveBox where uuid = :uuid and is_received = 0 and expired_at > :now", ReceiveBox.class)
                    .setParameter("uuid", uuid.toString())
                    .setParameter("now", now)
                    .getResultList();
            session.getTransaction().commit();
            return receiveBoxes;
        } finally {
            session.close();
        }
    }


    /**
     * 受取アイテムを更新します
     *
     * @return ReceiveBox
     */
    public ReceiveBox updateReceiveBox(ReceiveBox receiveBox){
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(receiveBox);
            session.getTransaction().commit();
            return receiveBox;
        } finally {
            session.close();
        }
    }

}
