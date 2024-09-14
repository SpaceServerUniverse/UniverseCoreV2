package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "lands")
public class Land {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", columnDefinition = "VARCHAR(255) NOT NULL")
    private String uuid;

    @Column(name = "start_x", nullable = false)
    private int start_x;

    @Column(name = "start_z", nullable = false)
    private int start_z;

    @Column(name = "end_x", nullable = false)
    private int end_x;

    @Column(name = "end_z", nullable = false)
    private int end_z;

    @Column(name = "world_name", columnDefinition = "VARCHAR(255) NOT NULL")
    private String world_name;

    @Column(name = "price", columnDefinition = "BIGINT UNSIGNED")
    private Long price;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    public Land (
            Long id,
            String uuid,
            int start_x,
            int start_z,
            int end_x,
            int end_z,
            String world_name,
            Long price,
            Date created_at
    ) {
        this.id = id;
        this.uuid = uuid;
        this.start_x = start_x;
        this.start_z = start_z;
        this.end_x = end_x;
        this.end_z = end_z;
        this.world_name = world_name;
        this.price = price;
        this.created_at = created_at;
    }

    public Land () {

    }

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_z() {
        return start_z;
    }

    public int getEnd_x() {
        return end_x;
    }

    public int getEnd_z() {
        return end_z;
    }

    public String getWorld_name() {
        return world_name;
    }

    public Long getPrice() {
        return price;
    }

    public Date getCreated_at() {
        return created_at;
    }

    @PrePersist
    protected void onCreate () {
        this.created_at = new Date ();
    }
}
