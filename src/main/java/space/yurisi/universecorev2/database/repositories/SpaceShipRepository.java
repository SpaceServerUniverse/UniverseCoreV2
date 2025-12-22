package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.LoginBonus;
import space.yurisi.universecorev2.database.models.SpaceShip;
import space.yurisi.universecorev2.exception.LoginBonusNotFoundException;
import space.yurisi.universecorev2.exception.SpaceShipNotFoundException;

import java.util.Date;
import java.util.List;

public class SpaceShipRepository {

    private final SessionFactory sessionFactory;

    public SpaceShipRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    /**
     * Playerに基づき宇宙船を作成します
     * @param player
     * @return 作成されたモデル
     */
    public SpaceShip create(Player player) {
        SpaceShip spaceShip = new SpaceShip(null, player.getUniqueId().toString(), 0L, new Date());
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(spaceShip);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return spaceShip;
    }

    /**
     * idに基づき宇宙船を取得します
     * @param id プライマリ
     * @return 検索結果
     * @throws SpaceShipNotFoundException 検索結果が存在しない
     */
    public SpaceShip getById(Long id) throws SpaceShipNotFoundException {
        try (Session session = this.sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            SpaceShip data = session.get(SpaceShip.class, id);
            session.getTransaction().commit();

            if (data == null) {
                throw new SpaceShipNotFoundException("宇宙船データが存在しませんでした。 ID:" + id);
            }

            return data;
        }
    }

    /**
     * プレイヤーに基づき宇宙船を取得します
     * @param player Playerオブジェクト
     * @return 取得結果 1件
     * @throws SpaceShipNotFoundException 検索結果が存在しない
     */
    public SpaceShip getByPlayer(Player player) throws SpaceShipNotFoundException {
        try (Session session = this.sessionFactory.getCurrentSession()) {
            String uuid = player.getUniqueId().toString();
            session.beginTransaction();
            SpaceShip data = session.createSelectionQuery("from SpaceShip where uuid = ?1", SpaceShip.class)
                    .setParameter(1, uuid).getSingleResultOrNull();
            session.getTransaction().commit();
            if (data == null) {
                throw new SpaceShipNotFoundException("宇宙船データが存在しませんでした。 UUID:" + uuid);
            }
            return data;
        }
    }

    /**
     * アップデートする
     * @param spaceShip 宇宙船モデル
     */
    public void update(SpaceShip spaceShip) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(spaceShip);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}
