package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "land_permissions")
public class LandPermission {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "land_id", columnDefinition = "BIGINT UNSIGNED NOT NULL")
    private Long land_id;

    @Column(name = "uuid", columnDefinition = "VARCHAR(255) NOT NULL")
    private String uuid;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    public LandPermission (
            Long id,
            Long land_id,
            String uuid,
            Date created_at
    ) {
        this.id = id;
        this.land_id = land_id;
        this.uuid= uuid;
        this.created_at = created_at;
    }

    public LandPermission () {

    }

    public Long getId () {
        return id;
    }

    public Long getLand_id() {
        return land_id;
    }

    public String getUuid () {
        return uuid;
    }

    public Date getCreated_at () {
        return created_at;
    }

    @PrePersist
    protected void onCreate () {
        this.created_at = new Date ();
    }
}
