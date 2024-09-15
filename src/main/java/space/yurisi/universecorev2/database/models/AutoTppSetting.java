package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "auto_tpp_settings")
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

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public AutoTppSetting(
            Long id,
            Long user_id,
            Boolean is_auto_accept,
            Date created_at,
            Date updated_at
    ){
        this.id = id;
        this.user_id = user_id;
        this.is_auto_accept = is_auto_accept;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

}
