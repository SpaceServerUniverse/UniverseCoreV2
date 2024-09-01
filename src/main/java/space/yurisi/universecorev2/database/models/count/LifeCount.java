package space.yurisi.universecorev2.database.models.count;

import jakarta.persistence.*;

@Entity
@Table(name = "life_counts")
public class LifeCount {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    private Long count_id;

    @Column(name = "fishing", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long fishing;

    @Column(name = "block_break", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long block_break;

    @Column(name = "block_place", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long block_place;

    @Column(name = "flower_place", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long flower_place;

    @Column(name = "wood_break", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long wood_break;

    public LifeCount(Long id, Long count_id, Long fishing, Long block_break, Long block_place, Long flower_place, Long wood_break) {
        this.id = id;
        this.count_id = count_id;
        this.fishing = fishing;
        this.block_break = block_break;
        this.block_place = block_place;
        this.flower_place = flower_place;
        this.wood_break = wood_break;
    }

    public LifeCount() {
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

    public Long getFishing() {
        return fishing;
    }

    public void setFishing(Long fishing) {
        this.fishing = fishing;
    }

    public Long getBlock_break() {
        return block_break;
    }

    public void setBlock_break(Long block_break) {
        this.block_break = block_break;
    }

    public Long getBlock_place() {
        return block_place;
    }

    public void setBlock_place(Long block_place) {
        this.block_place = block_place;
    }

    public Long getFlower_place() {
        return flower_place;
    }

    public void setFlower_place(Long flower_place) {
        this.flower_place = flower_place;
    }

    public Long getWood_break() {
        return wood_break;
    }

    public void setWood_break(Long wood_break) {
        this.wood_break = wood_break;
    }
}
