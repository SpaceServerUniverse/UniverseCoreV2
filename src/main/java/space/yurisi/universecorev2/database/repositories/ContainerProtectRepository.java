package space.yurisi.universecorev2.database.repositories;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.ContainerProtect;
import space.yurisi.universecorev2.exception.ContainerProtectNotFoundException;

import java.util.Date;

public class ContainerProtectRepository {

    private final SessionFactory sessionFactory;

    public ContainerProtectRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * コンテナ保護データを作成する
     *
     * @param player   プレイヤー
     * @param location チェストのロケーション
     * @return ContainerProtect
     */
    public ContainerProtect createContainerProtect(Player player, Location location) {
        String uuid = player.getUniqueId().toString();
        String world_name = location.getWorld().getWorldFolder().getName();

        Long x = (long) Math.floor(location.getX());
        Long y = (long) Math.floor(location.getY());
        Long z = (long) Math.floor(location.getZ());

        ContainerProtect data = new ContainerProtect(null, uuid, x, y, z, world_name, new Date(), new Date());

        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.persist(data);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

        return data;
    }

    /**
     * コンテナ保護データを取得する
     *
     * @param id コンテナ保護データID
     * @return ContainerProtect
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */
    public ContainerProtect getContainerProtect(Long id) throws ContainerProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            ContainerProtect data = session.get(ContainerProtect.class, id);
            session.getTransaction().commit();

            if (data == null) {
                throw new ContainerProtectNotFoundException("コンテナ保護データが見つかりません");
            }

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * コンテナ保護データをロケーションから取得
     *
     * @param location チェストのロケーション
     * @return ContainerProtect
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */
    public ContainerProtect getContainerProtectFromLocation(Location location) throws ContainerProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            ContainerProtect data = session.createQuery("FROM ContainerProtect WHERE x = :x AND y = :y AND z = :z AND world_name = :world_name", ContainerProtect.class)
                    .setParameter("x", (long) Math.floor(location.getX()))
                    .setParameter("y", (long) Math.floor(location.getY()))
                    .setParameter("z", (long) Math.floor(location.getZ()))
                    .setParameter("world_name", location.getWorld().getWorldFolder().getName())
                    .uniqueResult();
            session.getTransaction().commit();


            if (data == null) {
                throw new ContainerProtectNotFoundException("コンテナ保護データが見つかりません");
            }

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * コンテナ保護データをロケーションから取得
     *
     * @param location チェストのロケーション
     * @param player   プレイヤー
     * @return ContainerProtect
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */
    public ContainerProtect getContainerProtectFromLocation(Location location, Player player) throws ContainerProtectNotFoundException {
        String uuid = player.getUniqueId().toString();
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            ContainerProtect data = session.createQuery("FROM ContainerProtect WHERE x = :x AND y = :y AND z = :z AND world_name = :world_name AND uuid = :uuid", ContainerProtect.class)
                    .setParameter("x", (long) Math.floor(location.getX()))
                    .setParameter("y", (long) Math.floor(location.getY()))
                    .setParameter("z", (long) Math.floor(location.getZ()))
                    .setParameter("world_name", location.getWorld().getWorldFolder().getName())
                    .setParameter("uuid", uuid)
                    .uniqueResult();
            session.getTransaction().commit();
            if (data == null) {
                throw new ContainerProtectNotFoundException("コンテナ保護データが見つかりません");
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * コンテナ保護データをロケーションから存在するか確認
     *
     * @param location チェストのロケーション
     * @return Boolean
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */

    public Boolean existsContainerProtectFromLocation(Location location) {
        try {
            getContainerProtectFromLocation(location);
        } catch (ContainerProtectNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * コンテナ保護データをロケーションとプレイヤーから存在するか確認
     *
     * @param location チェストのロケーション
     * @param player   プレイヤー
     * @return Boolean
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */

    public Boolean existsContainerProtectFromLocation(Location location, Player player) {
        try {
            getContainerProtectFromLocation(location, player);
        } catch (ContainerProtectNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * コンテナ保護データを更新
     *
     * @param containerProtect コンテナ保護データ
     * @return ContainerProtect
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */
    public ContainerProtect updateContainerProtect(ContainerProtect containerProtect) throws ContainerProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(containerProtect);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return containerProtect;
    }

    /**
     * コンテナ保護データを削除
     *
     * @param containerProtect コンテナ保護データ
     * @throws ContainerProtectNotFoundException コンテナ保護データが見つからない場合
     */
    public void deleteContainerProtect(ContainerProtect containerProtect) throws ContainerProtectNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(containerProtect);
            session.getTransaction().commit();
        } finally {
            session.close();
        }

    }
}
