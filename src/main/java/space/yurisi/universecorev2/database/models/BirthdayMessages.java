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

    @Column(name = "birthday_data_id")
    private Long birthdayDataId;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "message")
    private String message;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    public BirthdayMessages(
            Long birthdayDataId,
            String uuid,
            String message
    ) {
        this.birthdayDataId = birthdayDataId;
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

    public Long getBirthdayDataId() {
        return birthdayDataId;
    }

    public void setBirthdayData(Long birthdayData) {
        this.birthdayDataId = birthdayData;
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
