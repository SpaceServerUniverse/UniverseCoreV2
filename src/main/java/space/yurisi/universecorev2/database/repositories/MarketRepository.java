package space.yurisi.universecorev2.database.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.yaml.snakeyaml.error.Mark;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.exception.MarketItemNotFoundException;

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
        Market market = new Market(null, uuid, itemName, displayName, serializedItemStack, serializedItemStackJson, serializedItemStackMetaJson, price);
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
        Market market = this.getItemFromId(id);
        market.setPrice(price);
        session.beginTransaction();
        session.createSelectionQuery("update Market set price = :price where id = :id", Market.class)
            .setParameter("price", price)
            .setParameter("id", id);
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
        session.createSelectionQuery("delete from Market where id = :id", Market.class)
                .setParameter("id", id);
        session.getTransaction().commit();
        session.close();
        return market;
    }
}
