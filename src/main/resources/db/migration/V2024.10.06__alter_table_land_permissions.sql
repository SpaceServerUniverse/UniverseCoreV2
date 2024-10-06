use SpaceServerUniverse;

ALTER TABLE land_permissions ADD UNIQUE(land_id, uuid);