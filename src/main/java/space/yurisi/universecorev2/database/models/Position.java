package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;

@Entity
@Table(name = "positions")
public class Position {

    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Position(
            Long id,
            String name
    ){
        this.id = id;
        this.name = name;
    }

    public Position(){

    }

    public String getName() {
        return name;
    }
}
