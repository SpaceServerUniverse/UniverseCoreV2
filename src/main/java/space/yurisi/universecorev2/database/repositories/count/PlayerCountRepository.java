package space.yurisi.universecorev2.database.repositories.count;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.count.Count;
import space.yurisi.universecorev2.database.models.count.PlayerCount;
import space.yurisi.universecorev2.exception.PlayerCountNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerCountRepository {
    private SessionFactory sessionFactory;

    public PlayerCountRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PlayerCount createPlayerCount(Count count) {
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        PlayerCount playerCount = new PlayerCount(null, count.getId(),1L,1L, simpleDateFormat.format(today));

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(playerCount);
        session.getTransaction().commit();
        session.close();

        return playerCount;
    }

    public PlayerCount getPlayerCount(Count count) throws PlayerCountNotFoundException{
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        PlayerCount data = session.createSelectionQuery("from PlayerCount where count_id = ?1", PlayerCount.class)
                .setParameter(1, count.getId())
                .getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new PlayerCountNotFoundException("プレイヤーカウントデータが存在しませんでした。");
        }
        return data;
    }

    public void updatePlayerCount(PlayerCount playerCount){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(playerCount);
        session.getTransaction().commit();
        session.close();
    }

    public boolean existsPlayerCount(Count count) {
        try {
            this.getPlayerCount(count);
            return true;
        } catch (PlayerCountNotFoundException exception) {
            return false;
        }
    }
}
