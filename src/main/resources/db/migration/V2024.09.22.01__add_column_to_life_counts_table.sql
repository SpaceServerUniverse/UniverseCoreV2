use SpaceServerUniverse;

ALTER TABLE life_counts
ADD COLUMN gacha BIGINT UNSIGNED NOT NULL DEFAULT 0 AFTER wood_break,
ADD COLUMN gacha_rarity_count BIGINT UNSIGNED NOT NULL DEFAULT 0 AFTER gacha,
ADD COLUMN gacha_ceiling_count BIGINT UNSIGNED NOT NULL DEFAULT 0 AFTER gacha_rarity_count;