package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.database.models.UserPosition;
import space.yurisi.universecorev2.exception.PositionNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.exception.UserPositionNotFoundException;

public class UserPositionRepository {
    private final SessionFactory sessionFactory;

    public UserPositionRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * ユーザーの役職をユーザーIDから取得します。
     *
     * @param id primary_key
     * @return Name
     * @throws UserPositionNotFoundException ユーザーの役職が存在しない
     */
    public UserPosition getUserPositionFromUserId(Long id) throws UserPositionNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            UserPosition data = session.createSelectionQuery("from UserPosition where user_id = ?1", UserPosition.class)
                    .setParameter(1, id).getSingleResultOrNull();
            session.getTransaction().commit();
            if (data == null) {
                throw new UserPositionNotFoundException("役職ユーザーデータが存在しませんでした。 ID:" + id);
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * ユーザーの役職をユーザーモデルから取得します。
     *
     * @param user User
     * @return Name
     * @throws UserPositionNotFoundException ユーザーの役職が存在しない
     */
    public UserPosition getUserPositionFromUser(User user) throws UserPositionNotFoundException {
        Long id = user.getId();
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            UserPosition data = session.createSelectionQuery("from UserPosition where user_id = ?1", UserPosition.class)
                    .setParameter(1, id).getSingleResultOrNull();
            session.getTransaction().commit();
            if (data == null) {
                throw new UserPositionNotFoundException("役職ユーザーデータが存在しませんでした。 ID:" + id);
            }
            return data;
        } finally {
            session.close();
        }
    }
}
