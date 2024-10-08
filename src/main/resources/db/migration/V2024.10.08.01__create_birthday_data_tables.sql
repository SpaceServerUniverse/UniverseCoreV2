#完全に完成した時に完成した日にする
use SpaceServerUniverse;

CREATE TABLE IF NOT EXISTS birthday_datas
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    uuid       VARCHAR(255) NOT NULL,
    month      INT          NOT NULL,
    day        INT          NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_month_day (month, day)
);
