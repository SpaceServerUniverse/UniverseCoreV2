package space.yurisi.universecorev2.database.repositories.count;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.database.models.count.OreCount;
import space.yurisi.universecorev2.exception.OreCountNotFoundException;

public class OreCountRepository {
    private SessionFactory sessionFactory;

    public OreCountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public OreCount createOreCount(Count count) {
        OreCount oreCount = new OreCount(null, count.getId(), 0L, 0L, 0L, 0L, 0L,0L,0L,0L);

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(oreCount);
        session.getTransaction().commit();
        session.close();

        return oreCount;
    }

    public OreCount getOreCount(Count count) throws OreCountNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        OreCount data = session.createSelectionQuery("from OreCount where count_id = ?1", OreCount.class)
                .setParameter(1, count.getId())
                .getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new OreCountNotFoundException("鉱石カウントデータが存在しませんでした。");
        }
        return data;
    }

    public void updateOreCount(OreCount oreCount){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(oreCount);
        session.getTransaction().commit();
        session.close();
    }

    public boolean existsOreCount(Count count) {
        try {
            this.getOreCount(count);
            return true;
        } catch (OreCountNotFoundException exception) {
            return false;
        }
    }
}
