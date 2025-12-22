package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.LevelReward;
import space.yurisi.universecorev2.exception.LevelRewardNotFoundException;

import java.util.Date;
import java.util.List;

public class LevelRewardRepository {
    private final SessionFactory sessionFactory;

    public LevelRewardRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Playerとレベルに基づきレベル報酬を作成します
     * @param player
     * @param level
     * @return
     */
    public LevelReward create(Player player, int level) {
        LevelReward levelReward = new LevelReward(null, player.getUniqueId().toString(), level, false, new Date());
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(levelReward);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return levelReward;
    }

    /**
     * idに基づきレベル報酬を取得します
     * @param id
     * @return
     * @throws space.yurisi.universecorev2.exception.LevelRewardNotFoundException
     */
    public LevelReward getById(Long id) throws LevelRewardNotFoundException {
        try (Session session = this.sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            LevelReward data = session.get(LevelReward.class, id);
            session.getTransaction().commit();

            if (data == null) {
                throw new LevelRewardNotFoundException("レベル報酬が存在しませんでした。 ID:" + id);
            }

            return data;
        }
    }

    /**
     * プレイヤーとレベルに基づきレベル報酬を取得します
     * @param player
     * @param level
     * @return
     * @throws LevelRewardNotFoundException
     */
    public LevelReward findByPlayer(Player player, int level) throws LevelRewardNotFoundException {
        try (Session session = this.sessionFactory.getCurrentSession()) {
            String uuid = player.getUniqueId().toString();
            session.beginTransaction();
            LevelReward data = session.createSelectionQuery("from LevelReward where uuid = ?1 and level = ?2", LevelReward.class)
                    .setParameter(1, uuid).setParameter(2, level).getSingleResultOrNull();
            session.getTransaction().commit();
            if (data == null) {
                throw new LevelRewardNotFoundException("レベル報酬データが存在しませんでした。 UUID:" + uuid);
            }
            return data;
        }
    }

    /**
     * プレイヤーのログインボーナスをすべて取得します
     * @param player
     * @return
     * @throws LevelRewardNotFoundException
     */
    public List<LevelReward> getAllByPlayer(Player player) {
        try (Session session = sessionFactory.getCurrentSession()) {
            String uuid = player.getUniqueId().toString();
            session.beginTransaction();
            try {
                List<LevelReward> list = session.createSelectionQuery(
                                "from LevelReward where uuid = :uuid",
                                LevelReward.class
                        )
                        .setParameter("uuid", uuid)
                        .getResultList();

                session.getTransaction().commit();
                return list;
            } catch (RuntimeException e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }

    /**
     * LevelRewardモデルに基づき、データをアップデートする
     * @param levelReward
     */
    public void update(LevelReward levelReward) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(levelReward);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}
