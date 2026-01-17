USE SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS slots (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid            VARCHAR(255) NOT NULL,
    x               BIGINT NOT NULL,
    y               BIGINT NOT NULL,
    z               BIGINT NOT NULL,
    world_name VARCHAR(255) NOT NULL,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    );