package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "birthday_data")
public class BirthdayData {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", unique = true, columnDefinition = "VARCHAR(255) NOT NULL")
    private String uuid;

    @Column(name = "month")
    private int month;

    @Column(name = "day")
    private int day;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public BirthdayData(
            Long id,
            String uuid,
            int month,
            int day
    ) {
        this.id = id;
        this.uuid = uuid;
        this.month = month;
        this.day = day;
    }

    public BirthdayData() {
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

    public int getMonth() {
        return month;

    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Set<BirthdayMessages> getBirthdayMessages() {
        return birthdayMessages;
    }

    public void setBirthdayMessages(Set<BirthdayMessages> birthdayMessages) {
        this.birthdayMessages = birthdayMessages;
    }

    public Date getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Date createAt) {
        this.createdAt = createAt;
    }
}
