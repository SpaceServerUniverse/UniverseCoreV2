package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "job")
public class Job {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "job_id")
    private Integer job_id;

    @Column(name= "last_changed")
    private Date last_changed;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    public Job(
            Long id,
            String uuid,
            Integer job_id,
            Date last_changed,
            Date created_at,
            Date updated_at
    ) {
        this.id = id;
        this.uuid = uuid;
        this.job_id = job_id;
        this.last_changed = last_changed;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Job() {

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

    public Integer getJob_id() {
        return job_id;
    }
    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    public Date getLast_changed() {
        return last_changed;
    }
    public void setLast_changed(Date last_changed) {
        this.last_changed = last_changed;
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
