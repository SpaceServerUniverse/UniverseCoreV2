USE SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS space_ships (
    id              BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid            VARCHAR(255)    NOT NULL,
    point           BIGINT          NOT NULL DEFAULT 0 UNIQUE,
    created_at      DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP
);