package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Slot;
import space.yurisi.universecorev2.exception.CannotReduceSlotCashException;
import space.yurisi.universecorev2.exception.SlotNotFoundException;

import java.util.Date;
import java.util.UUID;

public class SlotRepository {

    private final SessionFactory sessionFactory;

    public SlotRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * スロットをdbに作成します
     *
     * @return Slot
     */
    public Slot createSlot(Slot slot) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(slot);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return slot;
    }

    /**
     * スロットを作成します
     *
     * @return Slot
     */
    public Slot createSlot(UUID uuid, Long x, Long y, Long z, String world_name) {
        Slot slot = new Slot(null, uuid.toString(), x, y, z, world_name, 10000L, 1, new Date(), new Date());
        return createSlot(slot);
    }

    /**
     * スロットを座標から検索します
     *
     * @param x
     * @param y
     * @param z
     * @param world_name
     *
     * @return Slot
     */
    public Slot getSlotFromCoordinates(Long x, Long y, Long z, String world_name) throws SlotNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Slot slot = session.createSelectionQuery(
                            "FROM Slot WHERE x = :x AND y = :y AND z = :z AND world_name = :world_name",
                            Slot.class
                    )
                    .setParameter("x", x)
                    .setParameter("y", y)
                    .setParameter("z", z)
                    .setParameter("world_name", world_name)
                    .uniqueResult();
            session.getTransaction().commit();

            if(slot == null){
                throw new SlotNotFoundException("スロットが見つかりません");
            }

            return slot;
        } finally {
            session.close();
        }
    }

    public void updateCash(Slot slot, Long difference) throws CannotReduceSlotCashException {
        long newCash = slot.getCash() + difference;
        if(newCash < 0){
            throw new CannotReduceSlotCashException("スロットの所持金をこれ以上減らせません");
        }
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            slot.setCash(newCash);
            session.merge(slot);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void updateConfig(Slot slot, int newConfig) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            slot.setConfig(newConfig);
            session.merge(slot);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * スロットを削除します
     */
    public void deleteSlot(Slot slot) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(slot);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
