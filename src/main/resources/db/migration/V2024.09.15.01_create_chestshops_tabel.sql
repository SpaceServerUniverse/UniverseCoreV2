use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS chest_shops
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid        VARCHAR(255) NOT NULL,
    world_name  VARCHAR(255) NOT NULL,
    item        TEXT         NOT NULL,
    price       INT          NOT NULL,
    sign_x      INT          NOT NULL,
    sign_y      INT          NOT NULL,
    sign_z      INT          NOT NULL,
    mainChest_x INT          NOT NULL,
    mainChest_y INT          NOT NULL,
    mainChest_z INT          NOT NULL,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
