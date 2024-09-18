package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.PlayerNormalLevel;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.exception.PlayerLevelNotFoundException;
import space.yurisi.universecorev2.exception.PlayerNormalLevelNotFoundException;

import java.util.Date;

public class PlayerNormalLevelRepository {

    private final SessionFactory sessionFactory;

    public PlayerNormalLevelRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * ユーザーに基づきノーマルレベルを作成します。
     *
     * @param user UserModel
     * @return User | null
     */
    public PlayerNormalLevel createPlayerNormalLevel(User user) {
        PlayerNormalLevel level = new PlayerNormalLevel(
                null,
                user.getId(),
                0L,
                1,
                0L,
                0L,
                new Date(),
                new Date()
        );

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(level);//save
        session.getTransaction().commit();
        session.close();

        return level;
    }

    /**
     * ノーマルレベルデータをプライマリーキーから取得します。
     *
     * @param id Long(PrimaryKey)
     * @return User | null
     * @exception PlayerNormalLevelNotFoundException レベルデータが存在しない
     */
    public PlayerNormalLevel getPlayerNormalLevel(Long id) throws PlayerNormalLevelNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        PlayerNormalLevel data = session.get(PlayerNormalLevel.class, id);
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new PlayerNormalLevelNotFoundException("ユーザーデータが存在しませんでした。 ID:" + id);
        }
        return data;
    }

    /**
     * ノーマルレベルデータをユーザーモデルから取得します。
     *
     * @param user User
     * @return User | null
     * @exception PlayerNormalLevelNotFoundException レベルデータが存在しない
     */
    public PlayerNormalLevel getPlayerNormalLevelFromUser(User user) throws PlayerNormalLevelNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        PlayerNormalLevel data = session.createSelectionQuery("from PlayerNormalLevel where user_id = ?1", PlayerNormalLevel.class)
                .setParameter(1, user.getId()).getSingleResultOrNull();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new PlayerNormalLevelNotFoundException("ユーザーレベルデータが存在しませんでした。 UserId:" + user.getId());
        }
        return data;
    }


    /**
     * 指定したノーマルレベルデータがデータベースに存在するかを返します
     *
     * @param id Long
     * @return boolean
     */
    public boolean existsPlayerNormalLevel(Long id) {
        try {
            getPlayerNormalLevel(id);
            return true;
        } catch (PlayerNormalLevelNotFoundException e) {
            return false;
        }
    }

    /**
     * 指定したユーザーのノーマルレベルデータがデータベースに存在するかを返します
     *
     * @param user User
     * @return boolean
     */
    public boolean existsPlayerLevelFromUser(User user) {
        try {
            getPlayerNormalLevelFromUser(user);
            return true;
        } catch (PlayerNormalLevelNotFoundException e) {
            return false;
        }
    }

    /**
     * プレイヤーノーマルレベルモデルに基づきデータをアップデートします。
     *
     * @param level PlayerLevel
     */
    public void updatePlayerNormalLevel(PlayerNormalLevel level) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(level);//update
        session.getTransaction().commit();
        session.close();
    }

    /**
     * プレイヤーノーマルレベルモデルに基づきデータを削除します。
     *
     * @param level PlayerLevel
     */
    public void deletePlayerNormalLevel(PlayerNormalLevel level) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(level); //delete
        session.getTransaction().commit();
        session.close();
    }

}
