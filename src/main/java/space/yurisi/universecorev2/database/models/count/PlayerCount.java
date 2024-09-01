package space.yurisi.universecorev2.database.models.count;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "player_counts")
public class PlayerCount {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "count_id", unique = true, columnDefinition = "BIGINT UNSIGNED")
    private Long count_id;

    @Column(name = "login", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long login;

    @Column(name = "consecutive_login", columnDefinition = "BIGINT UNSIGNED", nullable = false)
    private Long consecutive_login;

    @Column(name = "last_login_date", nullable = false)
    private String last_login_date;

    public PlayerCount(Long id, Long count_id, Long login, Long consecutive_login, String last_login_date) {
        this.id = id;
        this.count_id = count_id;
        this.login = login;
        this.consecutive_login = consecutive_login;
        this.last_login_date = last_login_date;
    }

    public PlayerCount() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount_id() {
        return count_id;
    }

    public void setCount_id(Long count_id) {
        this.count_id = count_id;
    }

    public Long getLogin() {
        return login;
    }

    public void setLogin(Long login) {
        this.login = login;
    }

    public Long getConsecutive_login() {
        return consecutive_login;
    }

    public void setConsecutive_login(Long consecutive_login) {
        this.consecutive_login = consecutive_login;
    }

    public String getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(String last_login_date) {
        this.last_login_date = last_login_date;
    }
}
