CREATE TABLE user (
	id INTEGER NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE auth_token (
	id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    token VARCHAR(512) NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(token),
    FOREIGN KEY (user_id)
		REFERENCES user(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE article (
	id INTEGER NOT NULL AUTO_INCREMENT,
    uid VARCHAR(128) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    url VARCHAR(255) NOT NULL,
    written_date DATETIME(3) NOT NULL,
    used_in_train BOOLEAN NOT NULL DEFAULT false,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE source (
	id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE crawled_from (
	id INTEGER NOT NULL AUTO_INCREMENT,
    article_id INTEGER NOT NULL,
    source_id INTEGER NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(article_id, source_id),
    FOREIGN KEY(article_id)
		REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(source_id)
		REFERENCES source(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE read_article (
	id INTEGER NOT NULL AUTO_INCREMENT,
    article_id INTEGER NOT NULL,
    reader_id INTEGER NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(article_id, reader_id),
    FOREIGN KEY(article_id)
		REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(reader_id)
		REFERENCES user(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE article_image (
    id INTEGER NOT NULL AUTO_INCREMENT,
    article_id INTEGER NOT NULL,
    src VARCHAR(255) NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(article_id)
        REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE similarity (
    id INTEGER NOT NULL AUTO_INCREMENT,
    article1_id INTEGER NOT NULL,
    article2_id INTEGER NOT NULL,
    similarity DOUBLE NOT NULL,
    reg_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    FOREIGN KEY(article1_id)
        REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY(article2_id)
        REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);