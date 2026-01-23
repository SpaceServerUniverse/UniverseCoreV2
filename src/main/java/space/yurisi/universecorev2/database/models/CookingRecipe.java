package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_unlocked")
public class CookingRecipe {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_uuid", columnDefinition = "VARCHAR(255) NOT NULL", nullable = false)
    private String player_uuid;

    @Column(name = "recipe", columnDefinition = "BINARY(32) NOT NULL", nullable = false)
    private byte[] recipe;

    public CookingRecipe(
            Long id,
            String player_uuid,
            byte[] recipe
    ) {

        this.id = id;
        this.player_uuid = player_uuid;
        this.recipe = recipe;
    }

    public CookingRecipe() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlayerUuid() {
        return player_uuid;
    }

    public void setPlayerUuid(String playerUuid) {
        this.player_uuid = playerUuid;
    }

    public byte[] getRecipe() {
        return recipe;
    }

    public void setRecipe(byte[] recipe) {
        this.recipe = recipe;
    }
}
