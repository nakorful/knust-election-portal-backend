create TABLE IF NOT EXISTS `voter` (
    `student_id` varchar(255) PRIMARY KEY,
    `name` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL,
    `avatar` varchar(255) NOT NULL,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_on` TIMESTAMP DEFAULT  CURRENT_TIMESTAMP
);

create TABLE IF NOT EXISTS `candidate` (
    `id` INT(11) PRIMARY KEY,
    `name` varchar(255) NOT NULL,
    `position` varchar(255) NOT NULL,
    `created_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_on` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);