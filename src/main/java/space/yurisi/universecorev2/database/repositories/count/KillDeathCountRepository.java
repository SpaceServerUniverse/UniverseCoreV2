package space.yurisi.universecorev2.database.repositories.count;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.database.models.count.KillDeathCount;
import space.yurisi.universecorev2.exception.KillDeathCountNotFoundException;

public class KillDeathCountRepository {

    private SessionFactory sessionFactory;

    public KillDeathCountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public KillDeathCount createKillDeathCount(Count count) {
        KillDeathCount killDeathCount = new KillDeathCount(null, count.getId(), 0L, 0L, 0L, 0L, 0L);

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(killDeathCount);
        session.getTransaction().commit();
        session.close();

        return killDeathCount;
    }

    public KillDeathCount getKillDeathCount(Count count) throws KillDeathCountNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        KillDeathCount data = session.createSelectionQuery("from KillDeathCount where count_id = ?1", KillDeathCount.class)
                .setParameter(1, count.getId())
                .getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new KillDeathCountNotFoundException("キルデスカウントデータが存在しませんでした。");
        }
        return data;
    }

    public void updateKillDeathCount(KillDeathCount killDeathCount){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(killDeathCount);
        session.getTransaction().commit();
        session.close();
    }

    public boolean existsKillDeathCount(Count count) {
        try {
            this.getKillDeathCount(count);
            return true;
        } catch (KillDeathCountNotFoundException exception) {
            return false;
        }
    }
}
