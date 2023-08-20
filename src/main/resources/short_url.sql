CREATE
DATABASE `java` character set utf8mb4 collate utf8mb4_unicode_ci;

CREATE TABLE `short_url`
(
    `id`        int                                     NOT NULL AUTO_INCREMENT,
    `short_url` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
    `long_url`  varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;