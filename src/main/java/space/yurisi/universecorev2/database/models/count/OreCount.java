package space.yurisi.universecorev2.database.models.count;

import jakarta.persistence.*;

@Entity
@Table(name = "ore_counts")
public class OreCount {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    private Long count_id;

    @Column(name = "coal_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long coal_ore;

    @Column(name = "iron_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long iron_ore;

    @Column(name = "gold_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long gold_ore;

    @Column(name = "lapis_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long lapis_ore;

    @Column(name = "redstone_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long redstone_ore;

    @Column(name = "emerald_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long emerald_ore;

    @Column(name = "diamond_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long diamond_ore;

    @Column(name = "copper_ore", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long copper_ore;

    public OreCount(Long id, Long count_id, Long coal_ore, Long iron_ore, Long gold_ore, Long lapis_ore, Long redstone_ore, Long emerald_ore, Long diamond_ore, Long copper_ore) {
        this.id = id;
        this.count_id = count_id;
        this.coal_ore = coal_ore;
        this.iron_ore = iron_ore;
        this.gold_ore = gold_ore;
        this.lapis_ore = lapis_ore;
        this.redstone_ore = redstone_ore;
        this.emerald_ore = emerald_ore;
        this.diamond_ore = diamond_ore;
        this.copper_ore = copper_ore;
    }

    public OreCount() {
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

    public Long getCoal_ore() {
        return coal_ore;
    }

    public void setCoal_ore(Long coal_ore) {
        this.coal_ore = coal_ore;
    }

    public Long getIron_ore() {
        return iron_ore;
    }

    public void setIron_ore(Long iron_ore) {
        this.iron_ore = iron_ore;
    }

    public Long getGold_ore() {
        return gold_ore;
    }

    public void setGold_ore(Long gold_ore) {
        this.gold_ore = gold_ore;
    }

    public Long getLapis_ore() {
        return lapis_ore;
    }

    public void setLapis_ore(Long lapis_ore) {
        this.lapis_ore = lapis_ore;
    }

    public Long getRedstone_ore() {
        return redstone_ore;
    }

    public void setRedstone_ore(Long redstone_ore) {
        this.redstone_ore = redstone_ore;
    }

    public Long getEmerald_ore() {
        return emerald_ore;
    }

    public void setEmerald_ore(Long emerald_ore) {
        this.emerald_ore = emerald_ore;
    }

    public Long getDiamond_ore() {
        return diamond_ore;
    }

    public void setDiamond_ore(Long diamond_ore) {
        this.diamond_ore = diamond_ore;
    }

    public Long getCopper_ore(){
        return copper_ore;
    }

    public void setCopper_ore(Long copper_ore){
        this.copper_ore = copper_ore;
    }
}
