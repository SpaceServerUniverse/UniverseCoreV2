USE SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS login_bonus (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid            VARCHAR(255) NOT NULL,
    last_login_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    is_received     BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE uuid_last_login_date_index (uuid, last_login_date)
);