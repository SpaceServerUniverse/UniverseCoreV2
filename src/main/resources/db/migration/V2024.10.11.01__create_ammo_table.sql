use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS ammo(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNSIGNED NOT NULL,
    hg BIGINT UNSIGNED NOT NULL DEFAULT 120,
    smg BIGINT UNSIGNED NOT NULL DEFAULT 120,
    ar BIGINT UNSIGNED NOT NULL DEFAULT 120,
    sg BIGINT UNSIGNED NOT NULL DEFAULT 60,
    lmg BIGINT UNSIGNED NOT NULL DEFAULT 120,
    sr BIGINT UNSIGNED NOT NULL DEFAULT 60,
    ex BIGINT UNSIGNED NOT NULL DEFAULT 10,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);