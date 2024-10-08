use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS birthday_messages
(
    id               BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    birthday_data_id BIGINT UNSIGNED NOT NULL,
    uuid             VARCHAR(255)    NOT NULL,
    message          TEXT            NOT NULL,
    created_at       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (birthday_data_id) REFERENCES birthday_datas (id) ON DELETE CASCADE
);

