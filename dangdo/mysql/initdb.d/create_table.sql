CREATE TABLE `file` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `created_date` datetime NOT NULL,
          `updated_date` datetime DEFAULT NULL,
          `target_id` bigint(20) DEFAULT NULL,
          `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- DANGDO.menu_option definition

CREATE TABLE `menu_option` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `created_date` datetime NOT NULL,
          `updated_date` datetime DEFAULT NULL,
          `note` longtext COLLATE utf8mb4_unicode_ci,
          `type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- DANGDO.store definition

CREATE TABLE `store` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `created_date` datetime NOT NULL,
          `updated_date` datetime DEFAULT NULL,
          `business_hours` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `can_delivery` bit(1) DEFAULT NULL,
          `can_pickup` bit(1) DEFAULT NULL,
          `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `notice` longtext COLLATE utf8mb4_unicode_ci,
          `order_form` text COLLATE utf8mb4_unicode_ci,
          PRIMARY KEY (`id`),
          KEY `name_index` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- DANGDO.store_detail definition

CREATE TABLE `store_detail` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `business_hours` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `can_delivery` bit(1) DEFAULT NULL,
          `can_pickup` bit(1) DEFAULT NULL,
          `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `location` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `max_price` int(11) DEFAULT NULL,
          `min_price` int(11) DEFAULT NULL,
          `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `notice` longtext COLLATE utf8mb4_unicode_ci,
          `order_form` text COLLATE utf8mb4_unicode_ci,
          `rating` int(11) DEFAULT NULL,
          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- DANGDO.menu definition

CREATE TABLE `menu` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `created_date` datetime NOT NULL,
          `updated_date` datetime DEFAULT NULL,
          `category` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `caution` text COLLATE utf8mb4_unicode_ci,
          `description` text COLLATE utf8mb4_unicode_ci,
          `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `price` int(11) DEFAULT NULL,
          `summary` text COLLATE utf8mb4_unicode_ci,
          `store_id` bigint(20) DEFAULT NULL,
          PRIMARY KEY (`id`),
          KEY `FK4sgenfcmk1jajhgctnkpn5erg` (`store_id`),
          KEY `name_index` (`name`),
          CONSTRAINT `FK4sgenfcmk1jajhgctnkpn5erg` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- DANGDO.review definition

CREATE TABLE `review` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `created_date` datetime NOT NULL,
          `updated_date` datetime DEFAULT NULL,
          `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `dangdo` int(11) DEFAULT NULL,
          `good_point` smallint(6) DEFAULT NULL,
          `reorder` bit(1) DEFAULT NULL,
          `menu_id` bigint(20) DEFAULT NULL,
          PRIMARY KEY (`id`),
          KEY `FKkythy7xd59wvq6hwhv23xh7gw` (`menu_id`),
          CONSTRAINT `FKkythy7xd59wvq6hwhv23xh7gw` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- DANGDO.store_link definition

CREATE TABLE `store_link` (
          `id` bigint(20) NOT NULL AUTO_INCREMENT,
          `created_date` datetime NOT NULL,
          `updated_date` datetime DEFAULT NULL,
          `platform` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
          `store_id` bigint(20) DEFAULT NULL,
          PRIMARY KEY (`id`),
          KEY `FK3q35uxsk8mq3fxjjdm1rxvamu` (`store_id`),
          CONSTRAINT `FK3q35uxsk8mq3fxjjdm1rxvamu` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
