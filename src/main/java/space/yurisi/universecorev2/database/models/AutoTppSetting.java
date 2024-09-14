package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "autotppsetting")
public class AutoTppSetting {

    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "BIGINT UNSIGNED")
    private Long user_id;

    @Column(name = "is_auto_accept")
    @ColumnDefault("false")
    private Boolean is_auto_accept;

    public AutoTppSetting(
            Long id,
            Long user_id,
            Boolean is_auto_accept
    ){
        this.id = id;
        this.user_id = user_id;
        this.is_auto_accept = is_auto_accept;
    }

    public AutoTppSetting() {
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

    public Boolean getIs_auto_accept() {
        return is_auto_accept;
    }

    public void setIs_auto_accept(Boolean is_auto_accept) {
        this.is_auto_accept = is_auto_accept;
    }

}
