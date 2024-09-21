package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.yaml.snakeyaml.error.Mark;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.exception.MarketItemNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;

import java.util.List;

public class MarketRepository {

    private final SessionFactory sessionFactory;

    public MarketRepository(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    /**
     * フリーマーケット内のアイテムを取得します
     *
     * @return Market
     */
    public List<Market> getItems(){
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Market> market = session.createSelectionQuery("from Market ", Market.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return market;
    }

    /**
     * フリーマーケットからIDを用いて情報を取得します
     *
     * @param id Long
     * @return Market
     * @throws MarketItemNotFoundException アイテムがない
     */
    public Market getItemFromId(Long id) throws MarketItemNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Market data = session.get(Market.class, id);
        session.getTransaction().commit();
        session.close();
        if(data == null) {
            throw new MarketItemNotFoundException("マーケットにアイテムが存在しませんでした。id: "+id);
        }
        return data;
    }

    /**
     * プレイヤーの出品しているアイテムを取得します
     *
     * @param uuid uuid
     * @return List<Market>
     */
    public List<Market> getItemFromPlayer(String uuid) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Market> data = session.createSelectionQuery("from Market where playerUuid = :uuid", Market.class)
                .setParameter("uuid", uuid)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return data;
    }

    /**
     * アイテムを出品します
     *
     * @param uuid player_uuid
     * @param itemName display_name
     * @param serializedItemStack serialized itemStack
     * @param serializedItemStackJson serialized json
     * @param serializedItemStackMetaJson serialized json
     * @param price price
     * @return Market
     */
    public Market createItemData(String uuid, String itemName, String displayName, byte[] serializedItemStack, String serializedItemStackJson, String serializedItemStackMetaJson, Long price){
        Session session = this.sessionFactory.getCurrentSession();
        Market market = new Market(null, uuid, itemName, displayName, serializedItemStack, serializedItemStackJson, serializedItemStackMetaJson, price, 0, 0, null);
        session.beginTransaction();
        session.persist(market);
        session.getTransaction().commit();
        session.close();
        return market;
    }

    /**
     * アイテムの価格更新
     *
     * @param id Long_PrimaryKey
     * @param price Long
     * @return Market
     * @throws MarketItemNotFoundException
     */
    public Market updateItemPrice(Long id, Long price) throws MarketItemNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        Market market = removeItem(id);
        market.setPrice(price);
        session.beginTransaction();
        session.persist(market);
        session.getTransaction().commit();
        session.close();
        return market;
    }

    /**
     * 出品を取り下げます
     *
     * @param id Long_PrimaryKey
     * @return Market
     * @throws MarketItemNotFoundException
     */
    public Market removeItem(Long id) throws MarketItemNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        Market market = this.getItemFromId(id);
        session.beginTransaction();
        session.remove(market);
        session.getTransaction().commit();
        session.close();
        return market;
    }

    public void buyItem(Long id, Player player) throws MarketItemNotFoundException, UserNotFoundException, ParameterException, MoneyNotFoundException, CanNotReduceMoneyException {
        Market market = removeItem(id);
        UniverseEconomyAPI.getInstance().reduceMoney(player, market.getPrice());
        market.setSold(true);
        market.setReceivedItem(false);
        market.setPurchaserUuid(player.getUniqueId().toString());
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(market);
        session.getTransaction().commit();
        session.close();
    }

    public List<Market> getItemFromPurchaser(String uuid) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Market> markets = session.createSelectionQuery("from Market where purchaserUuid = :uuid and isReceivedItem = :is_received", Market.class)
                .setParameter("uuid", uuid)
                .setParameter("is_received", 0)
                .getResultList();
        return markets;
    }

    public void receiveItem(Long id, Player player) throws MarketItemNotFoundException {
        Market market = removeItem(id);
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        market.setReceivedItem(true);
        session.persist(market);
        session.getTransaction().commit();
        session.close();
    }
}
