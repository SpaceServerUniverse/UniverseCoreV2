use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS receive_boxes (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(255) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    description VARCHAR(255) NOT NULL,
    serialized_item BLOB NOT NULL,
    serialized_item_stack_json TEXT NOT NULL,
    serialized_item_meta_json TEXT NOT NULL,
    is_received INTEGER(1) NOT NULL DEFAULT 0,
    expired_at DATETIME NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);