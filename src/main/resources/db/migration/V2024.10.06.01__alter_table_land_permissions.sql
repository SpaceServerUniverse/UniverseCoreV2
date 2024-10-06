use SpaceServerUniverse;

DELETE lp1
FROM land_permissions lp1
JOIN land_permissions lp2
ON lp1.land_id = lp2.land_id
AND lp1.uuid = lp2.uuid
AND lp1.id > lp2.id;

ALTER TABLE land_permissions
ADD CONSTRAINT unique_land_id_uuid UNIQUE(land_id, uuid);