use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS market (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    player_uuid VARCHAR(255) NOT NULL,
    item_name TEXT NOT NULL,
    display_name TEXT,
    serialized_item BLOB NOT NULL,
    serialized_item_stack_json TEXT NOT NULL,
    serialized_item_meta_json TEXT NOT NULL,
    price BIGINT NOT NULL,
    is_sold CHAR(1) NOT NULL DEFAULT 0,
    is_received_item CHAR(1) NOT NULL DEFAULT 0,
    purchaser_uuid VARCHAR(255),
);