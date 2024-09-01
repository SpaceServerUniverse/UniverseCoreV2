package space.yurisi.universecorev2.database.repositories.count;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.exception.CountNotFoundException;

import java.util.List;

public class CountRepository {

    private final SessionFactory sessionFactory;

    public CountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Count createCount(User user){
        Count count = new Count(null, user.getId());

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(count);
        session.getTransaction().commit();
        session.close();

        return count;
    }

    public Count getCountFromUser(User user) throws CountNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Count data = session.createSelectionQuery("from Count where user_id = ?1", Count.class)
                .setParameter(1, user.getId())
                .getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new CountNotFoundException("カウントデータが存在しませんでした。");
        }
        return data;
    }

    public void updateCount(Count count){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(count);
        session.getTransaction().commit();
        session.close();
    }

    public boolean existsCountFromUser(User user){
        try{
            getCountFromUser(user);
            return true;
        }catch(CountNotFoundException exception){
            return false;
        }
    }

}
