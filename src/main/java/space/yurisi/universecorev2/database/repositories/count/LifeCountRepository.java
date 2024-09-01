package space.yurisi.universecorev2.database.repositories.count;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.database.models.count.LifeCount;
import space.yurisi.universecorev2.exception.LifeCountNotFoundException;

public class LifeCountRepository {
    private SessionFactory sessionFactory;

    public LifeCountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public LifeCount createLifeCount(Count count) {
        LifeCount LifeCount = new LifeCount(null, count.getId(), 0L, 0L, 0L, 0L, 0L);

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(LifeCount);
        session.getTransaction().commit();
        session.close();

        return LifeCount;
    }

    public LifeCount getLifeCount(Count count) throws LifeCountNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        LifeCount data = session.createSelectionQuery("from LifeCount where count_id = ?1", LifeCount.class)
                .setParameter(1, count.getId())
                .getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new LifeCountNotFoundException("生活カウントデータが存在しませんでした。");
        }
        return data;
    }

    public void updateLifeCount(LifeCount lifeCount){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(lifeCount);
        session.getTransaction().commit();
        session.close();
    }

    public boolean existsLifeCount(Count count) {
        try {
            this.getLifeCount(count);
            return true;
        } catch (LifeCountNotFoundException exception) {
            return false;
        }
    }
}
