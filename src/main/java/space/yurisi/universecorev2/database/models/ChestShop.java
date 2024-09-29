package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name="chest_shops")
public class ChestShop {

    @Id
    @Column(name = "id",unique = true,columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "uuid", columnDefinition = "VARCHAR(255) NOT NULL")
    private String uuid;

    @Column(name="world_name")
    private String world_name;

    @Column(name="item")
    private String item;

    @Column(name="price")
    private Long price;

    @Column(name = "sign_x")
    private Long sign_x;

    @Column(name = "sign_y")
    private Long sign_y;

    @Column(name = "sign_z")
    private Long sign_z;

    @Column(name = "mainChest_x")
    private Long mainChest_x;

    @Column(name = "mainChest_y")
    private Long mainChest_y;

    @Column(name = "mainChest_z")
    private Long mainChest_z;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    public ChestShop(
            Long id,
            String uuid,
            String world_name,
            String item,
            Long price,
            Long sign_x,
            Long sign_y,
            Long sign_z,
            Long mainChest_x,
            Long mainChest_y,
            Long mainChest_z,
            Date created_at
    ){
        this.uuid = uuid;
        this.world_name = world_name;
        this.item = item;
        this.price = price;
        this.sign_x = sign_x;
        this.sign_y = sign_y;
        this.sign_z = sign_z;
        this.mainChest_x = mainChest_x;
        this.mainChest_y = mainChest_y;
        this.mainChest_z = mainChest_z;
        this.created_at = created_at;
    }
    public ChestShop() {}

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUser_id(String uuid) {
        this.uuid = uuid ;
    }
    public String getWorld_name() {
        return world_name;
    }
    public void setWorld_name(String world_name) {
        this.world_name = world_name;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public long getPrice() {
        return price;
    }
    public void setPrice(Long price) {
        this.price = price;
    }
    public Long getSign_x() {
        return sign_x;
    }
    public void setSign_x(Long sign_x) {
        this.sign_x = sign_x;
    }
    public Long getSign_y() {
        return sign_y;
    }
    public void setSign_y(Long sign_y) {
        this.sign_y = sign_y;
    }
    public Long getSign_z() {
        return sign_z;
    }
    public void setSign_z(Long sign_z) {
        this.sign_z = sign_z;
    }
    public Long getMainChest_x() {
        return mainChest_x;
    }
    public void setMainChest_x(Long mainChest_x) {
        this.mainChest_x = mainChest_x;
    }
    public Long getMainChest_y() {
        return mainChest_y;
    }
    public void setMainChest_y(Long mainChest_y) {
        this.mainChest_y = mainChest_y;
    }
    public Long getMainChest_z() {
        return mainChest_z;
    }
    public void setMainChest_z(Long mainChest_z) {
        this.mainChest_z = mainChest_z;
    }
    public Date getCreated_at() {
        return created_at;
    }
   protected void onCreate(){
        this.created_at = new Date();
   }
}
