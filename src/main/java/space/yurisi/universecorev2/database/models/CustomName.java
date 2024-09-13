package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "custom_name")
public class CustomName{

    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", columnDefinition = "BIGINT UNSIGNED")
    private Long user_id;

    @Column(name = "display_custom_tag")
    private String display_custom_tag;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public CustomName(
            Long id,
            Long user_id,
            String display_custom_tag,
            Date created_at,
            Date updated_at
    ){
        this.id = id;
        this.user_id = user_id;
        this.display_custom_tag = display_custom_tag;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public CustomName(){}

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

    public String getDisplay_custom_tag() {
        return display_custom_tag;
    }

    public void setDisplay_custom_tag(String display_custom_tag) {
        this.display_custom_tag = display_custom_tag;
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
