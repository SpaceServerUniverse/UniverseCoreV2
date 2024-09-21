package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.jetbrains.annotations.Contract;

import java.sql.Blob;

@Entity
@Table(name = "market")
public class Market {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_uuid", columnDefinition = "VARCHAR(255) NOT NULL")
    private String playerUuid;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "serialized_item", nullable = false)
    private byte[] serializedItem;

    @Column(name = "serialized_item_stack_json", nullable = false)
    private String serializedItemStackJson;

    @Column(name = "serialized_item_meta_json", nullable = false)
    private String serializedItemStackMetaJson;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "is_sold")
    private boolean isSold;

    @Column(name = "is_received_item")
    private boolean isReceivedItem;

    @Column(name = "purchaser_uuid", columnDefinition = "VARCHAR(255)")
    private String purchaserUuid;

    public Market(
            Long id,
            String playerUuid,
            String itemName,
            String displayName,
            byte[] serializedItem,
            String serializedItemStackJson,
            String serializedItemStackMetaJson,
            Long price,
            int isSold,
            int isReceivedItem,
            String purchaserUuid
    ) {
        this.id = id;
        this.playerUuid = playerUuid;
        this.itemName = itemName;
        this.displayName = displayName;
        this.serializedItem = serializedItem;
        this.serializedItemStackJson = serializedItemStackJson;
        this.serializedItemStackMetaJson = serializedItemStackMetaJson;
        this.price = price;
        this.isSold = (isSold != 0);
        this.isReceivedItem = (isReceivedItem != 0);
        this.purchaserUuid = purchaserUuid;
    }

    public Market() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerUuid() {
        return playerUuid;
    }

    public void setPlayerUuid(String playerUuid) {
        this.playerUuid = playerUuid;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public byte[] getSerializedItem() {
        return serializedItem;
    }

    public void setSerializedItem(byte[] serializedItem) {
        this.serializedItem = serializedItem;
    }

    public String getSerializedItemStackJson() {
        return serializedItemStackJson;
    }

    public void setSerializedItemStackJson(String serializedItemStackJson) {
        this.serializedItemStackJson = serializedItemStackJson;
    }

    public String getSerializedItemStackMetaJson() {
        return serializedItemStackMetaJson;
    }

    public void setSerializedItemStackMetaJson(String serializedItemStackMetaJson) {
        this.serializedItemStackMetaJson = serializedItemStackMetaJson;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean isSold) {
        this.isSold = isSold;
    }

    public boolean isReceivedItem() {
        return isReceivedItem;
    }

    public void setReceivedItem(boolean isReceivedItem) {
        this.isReceivedItem = isReceivedItem;
    }

    public String getPurchaserUuid() {
        return purchaserUuid;
    }

    public void setPurchaserUuid(String purchaserUuid) {
        this.purchaserUuid = purchaserUuid;
    }
}
