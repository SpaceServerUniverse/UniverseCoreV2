package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "player_levels")
public class PlayerLevel {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, columnDefinition = "BIGINT UNSIGNED", nullable  = false)
    private Long user_id;

    @Column(name = "total_exp", columnDefinition = "BIGINT UNSIGNED", nullable  = false)
    @ColumnDefault("0")
    private Long total_exp;

    @Column(name = "level_mode", nullable  = false)
    @ColumnDefault("0")
    private int level_mode;

    @CreationTimestamp
    @Column(name = "created_at", nullable  = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public PlayerLevel(
            Long id,
            Long user_id,
            Long total_exp,
            int level_mode,
            Date created_at,
            Date updated_at
    ) {
        this.id = id;
        this.user_id = user_id;
        this.level_mode = level_mode;
        this.total_exp = total_exp;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }

    public PlayerLevel() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getTotal_exp() {
        return total_exp;
    }

    public void setTotal_exp(Long total_exp) {
        this.total_exp = total_exp;
    }

    public int getLevel_mode() {
        return level_mode;
    }

    public void setLevel_mode(int level_mode) {
        this.level_mode = level_mode;
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

    @PrePersist
    protected void onCreate() {
        this.updated_at = this.created_at = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated_at = new Date();
    }
}