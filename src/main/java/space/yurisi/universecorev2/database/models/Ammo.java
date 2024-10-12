package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "ammo")
public class Ammo {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    private Long user_id;

    @Column(name = "hg")
    @ColumnDefault("120")
    private Long hg;

    @Column(name = "smg")
    @ColumnDefault("120")
    private Long smg;

    @Column(name = "ar")
    @ColumnDefault("120")
    private Long ar;

    @Column(name = "sg")
    @ColumnDefault("60")
    private Long sg;

    @Column(name = "sr")
    @ColumnDefault("60")
    private Long sr;

    @Column(name = "lmg")
    @ColumnDefault("120")
    private Long lmg;

    @Column(name = "ex")
    @ColumnDefault("10")
    private Long ex;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public Ammo(
            Long id,
            Long user_id,
            Long hg,
            Long smg,
            Long ar,
            Long sg,
            Long sr,
            Long lmg,
            Long ex,
            Date created_at,
            Date updated_at
    ){
        this.id = id;
        this.user_id = user_id;
        this.hg = hg;
        this.smg = smg;
        this.ar = ar;
        this.sg = sg;
        this.sr = sr;
        this.lmg = lmg;
        this.ex = ex;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Ammo(){}

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

    public Long getHg() {
        return hg;
    }

    public void setHg(Long hg) {
        this.hg = hg;
    }

    public Long getSmg() {
        return smg;
    }

    public void setSmg(Long smg) {
        this.smg = smg;
    }

    public Long getAr() {
        return ar;
    }

    public void setAr(Long ar) {
        this.ar = ar;
    }

    public Long getSg() {
        return sg;
    }

    public void setSg(Long sg) {
        this.sg = sg;
    }

    public Long getSr() {
        return sr;
    }

    public void setSr(Long sr) {
        this.sr = sr;
    }

    public Long getLmg() {
        return lmg;
    }

    public void setLmg(Long lmg) {
        this.lmg = lmg;
    }

    public Long getEx() {
        return ex;
    }

    public void setEx(Long ex) {
        this.ex = ex;
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
