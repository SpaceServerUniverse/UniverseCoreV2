package space.yurisi.universecorev2.database.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "birthday_messages")
public class BirthdayMessages {
    @Id
    @Column(name = "id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "birthday_data_id", nullable = false)
    private BirthdayData birthdayData;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "message")
    private String message;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public BirthdayMessages(
            BirthdayData birthdayData,
            String uuid,
            String message
    ) {
        this.birthdayData = birthdayData;
        this.uuid = uuid;
        this.message = message;
    }

    public BirthdayMessages() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BirthdayData getBirthdayData() {
        return birthdayData;
    }

    public void setBirthdayData(BirthdayData birthdayData) {
        this.birthdayData = birthdayData;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

}
