package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;

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

    @Column(name = "last_gift_received_year")
    private Integer lastGiftReceivedYear;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public BirthdayData(
            Long id,
            String uuid,
            int month,
            int day,
            boolean giftReceived,
            Integer lastGiftReceivedYear
    ) {
        this.id = id;
        this.uuid = uuid;
        this.month = month;
        this.day = day;
        this.giftReceived = giftReceived;
        this.lastGiftReceivedYear = lastGiftReceivedYear;
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
        Integer year = lastGiftReceivedYear;
        if (year == null) {
            return false;
        }
        return giftReceived && year == LocalDate.now().getYear();
    }

    public void setGiftReceived(boolean giftReceived) {
        this.giftReceived = giftReceived;
    }

    public Integer getLastGiftReceivedYear() {
        return lastGiftReceivedYear;
    }

    public void setLastGiftReceivedYear(Integer lastGiftReceivedYear) {
        this.lastGiftReceivedYear = lastGiftReceivedYear;
    }

    public Date getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(Date createAt) {
        this.createdAt = createAt;
    }
}
