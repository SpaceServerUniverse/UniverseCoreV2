package space.yurisi.universecorev2.database.models.count;

import jakarta.persistence.*;

@Entity
@Table(name = "kill_death_counts")
public class KillDeathCount {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    private Long count_id;

    @Column(name = "player_kill", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long player_kill;

    @Column(name = "mob_kill", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long mob_kill;

    @Column(name = "ender_dragon_kill", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long ender_dragon_kill;

    @Column(name = "wither_kill", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long wither_kill;

    @Column(name = "death", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long death;

    public KillDeathCount(Long id, Long count_id, Long player_kill, Long mob_kill, Long ender_dragon_kill, Long wither_kill, Long death) {
        this.id = id;
        this.count_id = count_id;
        this.player_kill = player_kill;
        this.mob_kill = mob_kill;
        this.ender_dragon_kill = ender_dragon_kill;
        this.wither_kill = wither_kill;
        this.death = death;
    }

    public KillDeathCount(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount_id() {
        return count_id;
    }

    public void setCount_id(Long count_id) {
        this.count_id = count_id;
    }

    public Long getPlayer_kill() {
        return player_kill;
    }

    public void setPlayer_kill(Long player_kill) {
        this.player_kill = player_kill;
    }

    public Long getMob_kill() {
        return mob_kill;
    }

    public void setMob_kill(Long mob_kill) {
        this.mob_kill = mob_kill;
    }

    public Long getEnder_dragon_kill() {
        return ender_dragon_kill;
    }

    public void setEnder_dragon_kill(Long ender_dragon_kill) {
        this.ender_dragon_kill = ender_dragon_kill;
    }

    public Long getWither_kill() {
        return wither_kill;
    }

    public void setWither_kill(Long wither_kill) {
        this.wither_kill = wither_kill;
    }

    public Long getDeath() {
        return death;
    }

    public void setDeath(Long death) {
        this.death = death;
    }
}
