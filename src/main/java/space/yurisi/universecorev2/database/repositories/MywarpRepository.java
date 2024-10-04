package space.yurisi.universecorev2.database.repositories;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Mywarp;
import space.yurisi.universecorev2.exception.MywarpNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;

import java.util.Date;
import java.util.List;

public class MywarpRepository {
    private final SessionFactory sessionFactory;

    private final UserRepository userRepository;


    /**
     * Instantiates a new Mywarp repository.
     *
     * @param sessionFactory session factory
     * @param userRepository UserRepository
     */
    public MywarpRepository(SessionFactory sessionFactory, UserRepository userRepository) {
        this.sessionFactory = sessionFactory;
        this.userRepository = userRepository;
    }

    /**
     * マイワープを作成します。
     *
     * @param player     Player
     * @param warp_name  String
     * @param is_private Boolean
     * @return mywarp Mywarp
     */

//    mywarpの名前が被ったときの挙動をどうするかは未決定
    public Mywarp createMywarp(Player player, String warp_name, Boolean is_private) {
        Location location = player.getLocation();

        Long user_id = null;
        try {
            user_id = userRepository.getPrimaryKeyFromPlayerName(player.getName());
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        Long x = (long) Math.floor(location.getX());
        Long y = (long) Math.floor(location.getY());
        Long z = (long) Math.floor(location.getZ());
        String world_name = location.getWorld().getWorldFolder().getName();
        Mywarp mywarp = new Mywarp(null, user_id, warp_name, x, y, z, world_name, is_private, new Date(), new Date());

        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(mywarp);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return mywarp;
    }

    /**
     * マイワープをプライマリーキーから取得します。
     *
     * @param id Long(PrimaryKey)
     * @return Mywarp
     * @throws MywarpNotFoundException マイワープデータが存在しない
     */
    public Mywarp getMywarp(Long id) throws MywarpNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            Mywarp data = session.get(Mywarp.class, id);
            session.getTransaction().commit();
            if (data == null) {
                throw new MywarpNotFoundException("マイワープデータが見つかりません");
            }

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * マイワープをuser_idから取得します。
     *
     * @param user_id Long
     * @return List
     * @throws MywarpNotFoundException マイワープデータが存在しない
     */
    public List<Mywarp> getMywarpFromUserId(Long user_id) throws MywarpNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<Mywarp> data = session.createSelectionQuery("from Mywarp where user_id = ?1", Mywarp.class)
                    .setParameter(1, user_id).getResultList();
            session.getTransaction().commit();

            if (data.isEmpty()) {
                throw new MywarpNotFoundException("マイワープデータが見つかりません");
            }

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * マイワープを更新します。
     *
     * @param mywarp Mywarp
     * @return Mywarp
     * @throws MywarpNotFoundException マイワープデータが存在しない
     */
    public Mywarp updateMywarp(Mywarp mywarp) throws MywarpNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(mywarp);
            session.getTransaction().commit();
            return mywarp;
        } finally {
            session.close();
        }
    }

    /**
     * マイワープを削除します。
     *
     * @param mywarp Mywarp
     * @throws MywarpNotFoundException マイワープデータが存在しない
     */
    public void deleteMywarp(Mywarp mywarp) throws MywarpNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(mywarp);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * publicなマイワープを取得します。
     *
     * @param user_id Long
     * @return List
     * @throws MywarpNotFoundException マイワープデータが存在しない
     */
    public List<Mywarp> getPublicMywarpFromUserId(Long user_id) throws MywarpNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Mywarp> data = session.createSelectionQuery("from Mywarp where user_id = ?1 and is_private = false", Mywarp.class)
                    .setParameter(1, user_id).getResultList();
            session.getTransaction().commit();

            if (data.isEmpty()) {
                throw new MywarpNotFoundException("マイワープポイントが見つかりません");
            }
            data.removeIf(Mywarp::getIs_private);
            if (data.isEmpty()) {
                throw new MywarpNotFoundException("公開マイワープポイントが見つかりません");
            }

            return data;
        } finally {
            session.close();
        }
    }

}
