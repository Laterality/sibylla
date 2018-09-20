CREATE TABLE user (
	id INTEGER NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE auth_token (
	id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    token VARCHAR(255) NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(token),
    FOREIGN KEY (user_id)
		REFERENCES user(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE category (
	id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE preference (
	id INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    category_id INTEGER NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(user_id, category_id),
    FOREIGN KEY(user_id)
		REFERENCES user(id)
		ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(category_id)
		REFERENCES category(id)
		ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE keyword (
	id INTEGER NOT NULL AUTO_INCREMENT,
    keyword VARCHAR(255) NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE relate (
	id INTEGER NOT NULL AUTO_INCREMENT,
    category_id INTEGER NOT NULL,
    keyword_id INTEGER NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(category_id, keyword_id),
    FOREIGN KEY(category_id)
		REFERENCES category(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(keyword_id)
		REFERENCES keyword(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE article (
	id INTEGER NOT NULL AUTO_INCREMENT,
    uid VARCHAR(128) NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    url VARCHAR(255) NOT NULL,
    written_date TIMESTAMP NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE source (
	id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR(128) NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE tag (
	id INTEGER NOT NULL AUTO_INCREMENT,
    keyword_id INTEGER NOT NULL,
    article_id INTEGER NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(keyword_id, article_id),
    FOREIGN KEY(keyword_id)
		REFERENCES keyword(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(article_id)
		REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE crawled_from (
	id INTEGER NOT NULL AUTO_INCREMENT,
    article_id INTEGER NOT NULL,
    source_id INTEGER NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(article_id, source_id),
    FOREIGN KEY(article_id)
		REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(source_id)
		REFERENCES source(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE read (
	id INTEGER NOT NULL AUTO_INCREMENT,
    article_id INTEGER NOT NULL,
    reader_id INTEGER NOT NULL,
    reg_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    mod_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id),
    INDEX(article_id, reader_id),
    FOREIGN KEY(article_id)
		REFERENCES article(id)
        ON UPDATE CASCADE ON DELETE RESTRICT,
	FOREIGN KEY(reader_id)
		REFERENCES user(id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);