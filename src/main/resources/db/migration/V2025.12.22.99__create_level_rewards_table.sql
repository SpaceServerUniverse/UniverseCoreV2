USE SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS level_rewards (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid            VARCHAR(255) NOT NULL,
    level           INTEGER NOT NULL,
    is_received     BOOLEAN NOT NULL DEFAULT FALSE,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE uuid_level_index (uuid, level)
);