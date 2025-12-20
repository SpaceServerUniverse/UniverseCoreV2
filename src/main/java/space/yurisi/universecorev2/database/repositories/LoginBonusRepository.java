package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Land;
import space.yurisi.universecorev2.database.models.LoginBonus;
import space.yurisi.universecorev2.database.models.PlayerLevel;
import space.yurisi.universecorev2.exception.LandNotFoundException;
import space.yurisi.universecorev2.exception.LoginBonusNotFoundException;
import space.yurisi.universecorev2.exception.PlayerLevelNotFoundException;

import java.util.Date;
import java.util.UUID;

public class LoginBonusRepository {

    private final SessionFactory sessionFactory;

    public LoginBonusRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Playerに基づきログインボーナスを作成します
     * @param player
     * @return
     */
    public LoginBonus createLoginBonus(Player player) {
        LoginBonus loginBonus = new LoginBonus(null, player.getUniqueId().toString(), new Date(), false, new Date());
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(loginBonus);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return loginBonus;
    }

    /**
     * idに基づきログインボーナスを取得します
     * @param id
     * @return
     * @throws LoginBonusNotFoundException
     */
    public LoginBonus getLoginBonus(Long id) throws LoginBonusNotFoundException {
        try (Session session = this.sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            LoginBonus data = session.get(LoginBonus.class, id);
            session.getTransaction().commit();

            if (data == null) {
                throw new LoginBonusNotFoundException("ログインボーナスデータが存在しませんでした。 ID:" + id);
            }

            return data;
        }
    }

    /**
     * プレイヤーに基づきログインボーナスを取得します
     * @param player
     * @return
     * @throws LoginBonusNotFoundException
     */
    public LoginBonus getLoginBonusByPlayer(Player player) throws LoginBonusNotFoundException {
        try (Session session = this.sessionFactory.getCurrentSession()) {
            String uuid = player.getUniqueId().toString();
            session.beginTransaction();
            LoginBonus data = session.createSelectionQuery("from LoginBonus where uuid = ?1", LoginBonus.class)
                    .setParameter(1, uuid).getSingleResultOrNull();
            session.getTransaction().commit();
            if (data == null) {
                throw new LoginBonusNotFoundException("ログインボーナスデータが存在しませんでした。 UUID:" + uuid);
            }
            return data;
        }
    }

    /**
     *
     * @param loginBonus
     */
    public void update(LoginBonus loginBonus) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(loginBonus);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}
