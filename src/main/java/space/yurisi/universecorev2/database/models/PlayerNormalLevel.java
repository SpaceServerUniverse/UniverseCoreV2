package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "player_normal_levels")
public class PlayerNormalLevel {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long user_id;

    @Column(name = "exp", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    @ColumnDefault("0")
    private Long exp;

    @Column(name = "level", columnDefinition = "UNSIGNED", nullable = false)
    @ColumnDefault("1")
    private int level;

    @Column(name = "level_mode_total_exp", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    @ColumnDefault("0")
    private Long level_mode_total_exp;

    @Column(name = "exp_for_next_level", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    @ColumnDefault("0")
    private Long exp_for_next_level;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public PlayerNormalLevel(
            Long id,
            Long user_id,
            Long exp,
            int level,
            Long level_mode_total_exp,
            Long exp_for_next_level,
            Date created_at,
            Date updated_at
    ){
        this.id = id;
        this.user_id = user_id;
        this.exp = exp;
        this.level = level;
        this.level_mode_total_exp = level_mode_total_exp;
        this.exp_for_next_level = exp_for_next_level;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public PlayerNormalLevel(){}

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

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getLevel_mode_total_exp() {
        return level_mode_total_exp;
    }

    public void setLevel_mode_total_exp(Long level_mode_total_exp) {
        this.level_mode_total_exp = level_mode_total_exp;
    }

    public Long getExp_for_next_level() {
        return exp_for_next_level;
    }

    public void setExp_for_next_level(Long exp_for_next_level) {
        this.exp_for_next_level = exp_for_next_level;
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