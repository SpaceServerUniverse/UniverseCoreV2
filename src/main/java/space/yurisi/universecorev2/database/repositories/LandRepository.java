package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.Land;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.exception.LandNotFoundException;

import java.util.Date;
import java.util.List;


/**
 * The type Land repository.
 *
 * @author charindo
 * @version 1.0.0
 */
public class LandRepository {
    private final SessionFactory sessionFactory;
    private final LandRepository landRepository;

    /**
     * Instantiates a new User repository.
     *
     * @param sessionFactory session factory
     */
    public LandRepository(SessionFactory sessionFactory, LandRepository landRepository) {
        this.sessionFactory = sessionFactory;
        this.landRepository = landRepository;
    }

    /**
     * 土地保護データを作成します。
     *
     * @param player Player
     * @param start_x int
     * @param start_z int
     * @param end_x int
     * @param end_z int
     * @param world_name String
     * @return land Land
     */
    public Land createLand(Player player, int start_x, int start_z, int end_x, int end_z, String world_name, Long price) {
        Land land = new Land(null, player.getUniqueId().toString(), start_x, start_z, end_x, end_z, world_name, price, new Date());

        Session session = this.sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.persist(land);//save
        session.getTransaction().commit();
        session.close();

        return land;
    }

    /**
     * 土地保護データを取得します
     *
     * @param id Long(PrimaryKey)
     * @return Land | null
     * @throws LandNotFoundException 土地保護データが存在しない
     */
    public Land getLand(Long id) throws LandNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Land data = session.get(Land.class, id);
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new LandNotFoundException("土地保護データが存在しませんでした。 ID:" + id);
        }
        return data;
    }

    /**
     * 土地モデルに基づきデータをアップデートします。
     *
     * @param land Land
     */
    public void updateLand(Land land) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(land);
        session.getTransaction().commit();
        session.close();
    }

    public List<Land> getLands() throws LandNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Land> data = session.createSelectionQuery("from Land", Land.class).getResultList();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new LandNotFoundException("土地保護データが存在しませんでした。");
        }
        return data;
    }

    /**
     * 土地保護データを削除します。
     *
     * @param land Land
     */
    public void deleteLand(Land land) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(land); //delete
        session.getTransaction().commit();
        session.close();
    }
}