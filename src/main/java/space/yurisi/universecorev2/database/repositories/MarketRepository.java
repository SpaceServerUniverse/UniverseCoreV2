package space.yurisi.universecorev2.database.repositories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import space.yurisi.universecorev2.UniverseCoreV2API;
import space.yurisi.universecorev2.database.models.Market;
import space.yurisi.universecorev2.database.models.Money;
import space.yurisi.universecorev2.database.models.User;
import space.yurisi.universecorev2.exception.MarketItemNotFoundException;
import space.yurisi.universecorev2.exception.MoneyNotFoundException;
import space.yurisi.universecorev2.exception.OwnerBuyMarketItemException;
import space.yurisi.universecorev2.exception.UserNotFoundException;
import space.yurisi.universecorev2.subplugins.universeeconomy.UniverseEconomyAPI;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotAddMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.CanNotReduceMoneyException;
import space.yurisi.universecorev2.subplugins.universeeconomy.exception.ParameterException;
import space.yurisi.universecorev2.subplugins.universejob.UniverseJob;
import space.yurisi.universecorev2.subplugins.universejob.util.MarketPriceChanger;

import java.util.List;
import java.util.UUID;

public class MarketRepository {

    private final SessionFactory sessionFactory;

    public MarketRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * フリーマーケット内のアイテムを取得します
     *
     * @return Market
     */
    public List<Market> getItems() {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<Market> market = session.createSelectionQuery("from Market where isSold != :is_sold", Market.class)
                    .setParameter("is_sold", true)
                    .getResultList();
            session.getTransaction().commit();
            return market;
        } finally {
            session.close();
        }
    }

    /**
     * フリーマーケットからIDを用いて情報を取得します
     *
     * @param id                      Long
     * @param isPurchaseSearchEnabled bool true:売れてないアイテムのみから検索します false:すべての情報から検索します
     * @return Market
     * @throws MarketItemNotFoundException アイテムがない
     */
    public Market getItemFromId(Long id, boolean isPurchaseSearchEnabled) throws MarketItemNotFoundException {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            if (isPurchaseSearchEnabled) {
                session.beginTransaction();
                List<Market> data = session.createSelectionQuery("from Market where isSold != :is_sold and id = :id", Market.class)
                        .setParameter("id", id)
                        .setParameter("is_sold", true)
                        .getResultList();
                session.getTransaction().commit();
                if (data.isEmpty()) {
                    throw new MarketItemNotFoundException("マーケットにアイテムが存在しませんでした。id: " + id);
                }
                return data.getFirst();
            }

            session.beginTransaction();
            Market data = session.get(Market.class, id);
            session.getTransaction().commit();
            session.close();
            if (data == null) {
                throw new MarketItemNotFoundException("マーケットにアイテムが存在しませんでした。id: " + id);
            }
            return data;
        } finally {
            session.close();
        }
    }

    /**
     * プレイヤーの出品しているアイテムを取得します
     *
     * @param uuid uuid
     * @return List
     */
    public List<Market> getItemFromPlayer(String uuid, boolean isPurchaseSearchEnabled) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<Market> data;
            if (isPurchaseSearchEnabled) {
                data = session.createSelectionQuery("from Market where playerUuid = :uuid and isSold != :is_sold", Market.class)
                        .setParameter("uuid", uuid)
                        .setParameter("is_sold", true)
                        .getResultList();
            } else {
                data = session.createSelectionQuery("from Market where playerUuid = :uuid", Market.class)
                        .setParameter("uuid", uuid)
                        .getResultList();
                session.getTransaction().commit();
            }
            session.getTransaction().commit();

            return data;
        } finally {
            session.close();
        }
    }

    /**
     * アイテムを出品します
     *
     * @param uuid                        player_uuid
     * @param itemName                    display_name
     * @param serializedItemStack         serialized itemStack
     * @param serializedItemStackJson     serialized json
     * @param serializedItemStackMetaJson serialized json
     * @param price                       price
     * @return Market
     */
    public Market createItemData(String uuid, String itemName, String displayName, byte[] serializedItemStack, String serializedItemStackJson, String serializedItemStackMetaJson, Long price) {
        Session session = this.sessionFactory.getCurrentSession();
        Market market = new Market(null, uuid, itemName, displayName, serializedItemStack, serializedItemStackJson, serializedItemStackMetaJson, price, 0, 0, null);
        try {
            session.beginTransaction();
            session.persist(market);
            session.getTransaction().commit();

        } finally {
            session.close();
        }
        return market;
    }

    /**
     * 出品を取り下げます
     *
     * @param id Long_PrimaryKey
     * @return Market
     * @throws MarketItemNotFoundException
     */
    public Market removeItem(Long id, boolean isPurchaseSearchEnabled) throws MarketItemNotFoundException {
        Market market = this.getItemFromId(id, isPurchaseSearchEnabled);
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.remove(market);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
        return market;
    }

    public void buyItem(Long id, Player player) throws MarketItemNotFoundException, UserNotFoundException, ParameterException, MoneyNotFoundException, CanNotReduceMoneyException, CanNotAddMoneyException, OwnerBuyMarketItemException {
        Market market = getItemFromId(id, true);
        if(market.getPlayerUuid().equals(player.getUniqueId().toString())){
            throw new OwnerBuyMarketItemException("自分の出品しているアイテムは購入できません。");
        }

        ItemStack itemStack = ItemStack.deserializeBytes(market.getSerializedItem());

        long sellerPrice = MarketPriceChanger.sellerPriceChanger(market.getPlayerUuid(), itemStack, market.getPrice());
        long purchaserPrice = MarketPriceChanger.purchaserPriceChanger(player, itemStack, market.getPrice());
        if(sellerPrice < 1){
            sellerPrice = 1;
        }
        if(purchaserPrice < 1){
            purchaserPrice = 1;
        }

        UniverseEconomyAPI.getInstance().reduceMoney(player, purchaserPrice, "フリーマーケット[購入]");
        User user = UniverseCoreV2API.getInstance().getDatabaseManager().getUserRepository().getUserFromUUID(UUID.fromString(market.getPlayerUuid()));
        Money money = UniverseCoreV2API.getInstance().getDatabaseManager().getMoneyRepository().getMoneyFromUserId(user.getId());
        money.setMoney(money.getMoney() + sellerPrice);
        UniverseCoreV2API.getInstance().getDatabaseManager().getMoneyRepository().updateMoney(money, sellerPrice, "フリーマーケット[売却]");
        market.setSold(true);
        market.setReceivedItem(false);
        market.setPurchaserUuid(player.getUniqueId().toString());
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(market);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public void addPurchased(Market market, Player player) {
        market.setSold(true);
        market.setReceivedItem(false);
        market.setPurchaserUuid(player.getUniqueId().toString());
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            session.merge(market);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<Market> getItemFromPurchaser(String uuid) {
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            List<Market> markets = session.createSelectionQuery("from Market where purchaserUuid = :uuid and isReceivedItem = :is_received", Market.class)
                    .setParameter("uuid", uuid)
                    .setParameter("is_received", false)
                    .getResultList();
            session.getTransaction().commit();
            return markets;
        } finally {
            session.close();
        }
    }

    public void receiveItem(Long id, Player player) throws MarketItemNotFoundException {
        Market market = removeItem(id, false);
        Session session = this.sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();
            market.setReceivedItem(true);
            session.merge(market);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
