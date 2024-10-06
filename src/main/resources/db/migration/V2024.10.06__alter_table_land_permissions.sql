use SpaceServerUniverse;

DELETE FROM land_permissions WHERE id NOT IN ( SELECT MIN(id) FROM land_permissions GROUP BY land_id, uuid) );

ALTER TABLE land_permissions ADD UNIQUE(land_id, uuid);