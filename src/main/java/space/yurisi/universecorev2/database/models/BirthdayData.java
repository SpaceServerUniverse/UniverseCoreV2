package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "birthday_datas")
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

    @Column(name = "gift_received", nullable = false)
    private boolean giftReceived;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public BirthdayData(
            Long id,
            String uuid,
            int month,
            int day,
            boolean giftReceived
    ) {
        this.id = id;
        this.uuid = uuid;
        this.month = month;
        this.day = day;
        this.giftReceived = giftReceived;
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
    public boolean isGiftReceived() {
        return giftReceived;
    }
    public void setGiftReceived(boolean giftReceived) {
        this.giftReceived = giftReceived;
    }

    public Date getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Date createAt) {
        this.createdAt = createAt;
    }
}
