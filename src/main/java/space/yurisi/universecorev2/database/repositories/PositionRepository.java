package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Position;
import space.yurisi.universecorev2.database.models.UserPosition;
import space.yurisi.universecorev2.exception.PlayerLevelNotFoundException;
import space.yurisi.universecorev2.exception.PositionNotFoundException;

public class PositionRepository {

    private final SessionFactory sessionFactory;

    public PositionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 役職名をプライマリーキーから取得します。
     *
     * @param id primary_key
     * @return Name
     * @exception PositionNotFoundException 役職名が存在しない
     */
    public String getNameFromId(Long id) throws PositionNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Position data = session.get(Position.class, id);
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new PositionNotFoundException("役職データが存在しませんでした。 ID:" + id);
        }
        return data.getName();
    }

    /**
     * 役職名を役職ユーザーモデルから取得します。
     *
     * @param userPosition UserPosition
     * @return Name
     * @exception PositionNotFoundException 役職名が存在しない
     */
    public String getNameFromUserPosition(UserPosition userPosition) throws PositionNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            Long id = userPosition.getPosition_id();
            session.beginTransaction();
            Position data = session.get(Position.class, id);
            session.getTransaction().commit();

            if (data == null) {
                throw new PositionNotFoundException("役職データが存在しませんでした。 ID:" + id);
            }
            return data.getName();
        } finally {
            session.close();
        }
    }
}
