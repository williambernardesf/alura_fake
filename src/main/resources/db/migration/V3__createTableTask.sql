CREATE TABLE Task (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    statement VARCHAR(255) NOT NULL,
    order_value INT UNSIGNED NOT NULL, -- garante positivo
    type ENUM('OPEN_TEXT', 'SINGLE_CHOICE')
         CHARACTER SET utf8mb4
         COLLATE utf8mb4_unicode_ci NOT NULL,
    course_id BIGINT(20) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_Course FOREIGN KEY (course_id)
        REFERENCES Course(id) ON DELETE CASCADE,
    CONSTRAINT uq_course_statement UNIQUE (course_id, statement) -- impede duplicidade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;