use SpaceServerUniverse;

ALTER TABLE birthday_messages
    ADD COLUMN received_gacha_ticket BOOLEAN NOT NULL DEFAULT FALSE;
