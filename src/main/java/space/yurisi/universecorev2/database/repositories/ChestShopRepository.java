package space.yurisi.universecorev2.database.repositories;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.database.models.ChestShop;
import space.yurisi.universecorev2.exception.ChestShopNotFoundException;

import java.util.Date;
import java.util.List;

public class ChestShopRepository {
    private final SessionFactory sessionFactory;

    public ChestShopRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * チェストショップデータを作成します
     *
     * @param player    Player
     * @param itemStack itemStack
     * @param price     int
     * @param sign      Block
     * @param chest     Chest
     * @return chestshop
     */
    public ChestShop createChestShop(Player player, ItemStack itemStack, Long price, Block sign, Block chest) {
        Gson gson = new Gson();
        String uuid = player.getUniqueId().toString();
        Location chestLocation = chest.getLocation();
        Location signLocation = sign.getLocation();
        String worldName = chestLocation.getWorld().getWorldFolder().getName();
        String item = gson.toJson(itemStack.serialize());
        long signX = (long) Math.floor(signLocation.getX());
        long signY = (long) Math.floor(signLocation.getY());
        long signZ = (long) Math.floor(signLocation.getZ());
        long mainChestX = (long) Math.floor(chestLocation.getX());
        long mainChestY = (long) Math.floor(chestLocation.getY());
        long mainChestZ = (long) Math.floor(chestLocation.getZ());

        ChestShop chestShop = new ChestShop(null, uuid, worldName, item, price, signX, signY, signZ, mainChestX, mainChestY, mainChestZ, new Date());

        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(chestShop);
        session.getTransaction().commit();
        session.close();

        return chestShop;
    }

    /***
     *  チェストショップデータを取得します
     *
     * @param id Long(PrimaryKey)
     * @return ChestShop | null
     * @throws ChestShopNotFoundException
     */
    public ChestShop getChestShop(Long id) throws ChestShopNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        ChestShop data = session.get(ChestShop.class, id);
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new ChestShopNotFoundException("チェストショップデータが存在しませんでした");
        }
        return data;
    }

    /**
     * チェストショップモデルに基づきデータをアップデートします
     *
     * @param chestShop
     */
    public void updateChestShop(ChestShop chestShop) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(chestShop);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * すべてのチェストショップのデータを取得します
     *
     * @return
     * @throws ChestShopNotFoundException
     */
    public List<ChestShop> getChestShops() throws ChestShopNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<ChestShop> data = session.createSelectionQuery("FROM ChestShop", ChestShop.class).getResultList();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new ChestShopNotFoundException("チェストショップデータが存在しませんでした");
        }
        return data;
    }

    /**
     * チェストショップデータを削除します
     *
     * @param chestShop
     */
    public void deleteChestShop(ChestShop chestShop) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.remove(chestShop);
        session.getTransaction().commit();
        session.close();
    }

    public ChestShop getChestShopByLocation(Location location) throws ChestShopNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        ChestShop data = session.createQuery("FROM ChestShop WHERE mainChest_x = :x AND mainChest_y = :y AND mainChest_z = :z AND world_name = :world_name", ChestShop.class)
                .setParameter("x", location.getX())
                .setParameter("y", location.getY())
                .setParameter("z", location.getZ())
                .setParameter("world_name", location.getWorld().getWorldFolder().getName())
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new ChestShopNotFoundException("チェストショップデータが見つかりません");
        }
        return data;

    }

    public ChestShop getChestShopBySignLocation(Location location) throws ChestShopNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        ChestShop data = session.createQuery("FROM ChestShop WHERE sign_x = :x AND sign_y = :y AND sign_z = :z AND world_name = :world_name", ChestShop.class)
                .setParameter("x", location.getX())
                .setParameter("y", location.getY())
                .setParameter("z", location.getZ())
                .setParameter("world_name", location.getWorld().getWorldFolder().getName())
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        if (data == null) {
            throw new ChestShopNotFoundException("チェストショップデータが見つかりませんでした");
        }
        return data;
    }

    public boolean existsChestShopChest(Location location){
        try {
            getChestShopByLocation(location);
        } catch (ChestShopNotFoundException e) {
            return false;
        }
        return true;
    }

    public boolean existsChestShopSign(Location location){
        try{
            getChestShopBySignLocation(location);
        }catch(ChestShopNotFoundException e){
            return false;
        }
        return true;
    }

}
