package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "login_bonus")
public class LoginBonus {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "last_login_date")
    private Date last_login_date;

    @Column(name = "is_received")
    private Boolean is_received;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    public LoginBonus (
            Long id,
            String uuid,
            Date last_login_date,
            Boolean is_received,
            Date created_at
    ) {
        this.id = id;
        this.uuid = uuid;
        this.last_login_date = last_login_date;
        this.is_received = is_received;
        this.created_at = created_at;
    }

    public LoginBonus () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
    }

    public Boolean getIs_received() {
        return is_received;
    }

    public void setIs_received(Boolean is_received) {
        this.is_received = is_received;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
