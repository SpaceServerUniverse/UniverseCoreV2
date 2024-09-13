package space.yurisi.universecorev2.database.repositories;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.TileProtect;
import space.yurisi.universecorev2.exception.TileProtectNotFoundException;

import java.util.Date;

public class TileProtectRepository {

    private final SessionFactory sessionFactory;

    public TileProtectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * タイル保護データを作成する
     *
     * @param player プレイヤー
     * @param location チェストのロケーション
     * @return TileProtect
     */
    public TileProtect createTileProtect(Player player, Location location) {
        String uuid = player.getUniqueId().toString();
        String world_name = location.getWorld().getWorldFolder().getName();

        Long x = (long) Math.floor(location.getX());
        Long y = (long) Math.floor(location.getY());
        Long z = (long) Math.floor(location.getZ());

        TileProtect data = new TileProtect(null, uuid, x, y, z, world_name, new Date(), new Date());

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(data);
        session.getTransaction().commit();
        session.close();

        return data;
    }

    /**
     * タイル保護データを取得する
     *
     * @param id タイル保護データID
     * @return TileProtect
     * @throws TileProtectNotFoundException タイル保護データが見つからない場合
     */
    public TileProtect getTileProtect(Long id) throws TileProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        TileProtect data = session.get(TileProtect.class, id);
        session.getTransaction().commit();
        session.close();

        if (data == null) {
            throw new TileProtectNotFoundException("タイル保護データが見つかりません");
        }

        return data;
    }

    /**
     * タイル保護データをロケーションから取得
     *
     * @param location チェストのロケーション
     * @return TileProtect
     * @throws TileProtectNotFoundException タイル保護データが見つからない場合
     */
    public TileProtect getTileProtectFromLocation(Location location) throws TileProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        TileProtect data = session.createQuery("FROM TileProtect WHERE x = :x AND y = :y AND z = :z AND world_name = :world_name", TileProtect.class)
                .setParameter("x", (long) Math.floor(location.getX()))
                .setParameter("y", (long) Math.floor(location.getY()))
                .setParameter("z", (long) Math.floor(location.getZ()))
                .setParameter("world_name", location.getWorld().getWorldFolder().getName())
                .uniqueResult();
        session.getTransaction().commit();
        session.close();

        if (data == null) {
            throw new TileProtectNotFoundException("タイル保護データが見つかりません");
        }

        return data;
    }

    /**
     * タイル保護データを更新
     *
     * @param tileProtect タイル保護データ
     * @return TileProtect
     * @throws TileProtectNotFoundException タイル保護データが見つからない場合
     */
    public TileProtect updateTileProtect(TileProtect tileProtect) throws TileProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(tileProtect);
        session.getTransaction().commit();
        session.close();
        return tileProtect;
    }

    /**
     * タイル保護データを削除
     *
     * @param tileProtect タイル保護データ
     * @throws TileProtectNotFoundException タイル保護データが見つからない場合
     */
    public void deleteTileProtect(TileProtect tileProtect) throws TileProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(tileProtect);
        session.getTransaction().commit();
        session.close();
    }
}
