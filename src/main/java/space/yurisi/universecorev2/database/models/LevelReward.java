package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "level_rewards")
public class LevelReward {
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean isReceived() {
        return is_received;
    }

    public void setReceived(Boolean is_received) {
        this.is_received = is_received;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "level")
    private Integer level;

    @Column(name = "is_received")
    private Boolean is_received;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    public LevelReward(
            Long id,
            String uuid,
            Integer level,
            Boolean is_received,
            Date created_at
    ) {
        this.id = id;
        this.uuid = uuid;
        this.level = level;
        this.is_received = is_received;
        this.created_at = created_at;
    }

    public LevelReward() {

    }
}