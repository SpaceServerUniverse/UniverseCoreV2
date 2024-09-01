use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS money_histories(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    money_change BIGINT NOT NULL,
    money BIGINT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);