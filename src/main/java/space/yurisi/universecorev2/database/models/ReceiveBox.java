package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "receive_boxes")
public class ReceiveBox {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", columnDefinition = "VARCHAR(255) NOT NULL")
    private String uuid;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "description")
    private String description;

    @Column(name = "serialized_item", nullable = false)
    private byte[] serializedItem;

    @Column(name = "serialized_item_stack_json", nullable = false)
    private String serializedItemStackJson;

    @Column(name = "serialized_item_meta_json", nullable = false)
    private String serializedItemStackMetaJson;

    @Column(name = "is_received")
    private boolean is_received;

    @Column(name = "expired_at", nullable = false)
    private Date expired_at;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public ReceiveBox(
            Long id,
            String uuid,
            String itemName,
            String displayName,
            String description,
            byte[] serializedItem,
            String serializedItemStackJson,
            String serializedItemStackMetaJson,
            int is_received,
            Date expired_at
    ) {
        this.id = id;
        this.uuid = uuid;
        this.itemName = itemName;
        this.displayName = displayName;
        this.description = description;
        this.serializedItem = serializedItem;
        this.serializedItemStackJson = serializedItemStackJson;
        this.serializedItemStackMetaJson = serializedItemStackMetaJson;
        this.is_received = (is_received != 0);
        this.expired_at = expired_at;
    }

    public ReceiveBox() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
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

    public boolean isReceived() {
        return is_received;
    }

    public void setReceived(boolean is_received) {
        this.is_received = is_received;
    }

    public Date getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(Date expired_at) {
        this.expired_at = expired_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
