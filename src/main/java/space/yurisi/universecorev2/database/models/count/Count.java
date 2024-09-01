package space.yurisi.universecorev2.database.models.count;

import jakarta.persistence.*;

@Entity
@Table(name = "counts")
public class Count {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    private Long user_id;

    public Count(
            Long id,
            Long user_id
    ){
        this.id = id;
        this.user_id = user_id;
    }

    public Count () {

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
}
