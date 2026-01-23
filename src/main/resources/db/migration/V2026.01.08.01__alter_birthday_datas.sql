USE SpaceServerUniverse;

ALTER TABLE birthday_datas
    ADD COLUMN last_gift_received_year INT NULL
    AFTER gift_received;
