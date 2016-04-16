CREATE TABLE tb_locate (
longitude DECIMAL(8,6) NOT NULL,
latitude DECIMAL(8,6) NOT NULL,	
address VARCHAR(150)
)ENGINE = InnoDB;

CREATE TABLE tb_event (
idEvent INTEGER NOT NULL,
idOwner INTEGER NOT NULL,
nameEvent VARCHAR(30) NOT NULL,
dateTimeEvent DATETIME NOT NULL,
description VARCHAR(500) NOT NULL,
address VARCHAR(150),
longitude DECIMAL(8,6) NOT NULL,
latitude DECIMAL(8,6) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE tb_user (
idUser INTEGER NOT NULL,
nameUser VARCHAR(50) NOT NULL,
login VARCHAR(20) NOT NULL,
passwordUser VARCHAR(50)NOT NULL,
birthDate DATE NOT NULL,
isActivity CHAR(1) NOT NULL DEFAULT 'Y',
email VARCHAR(50) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE tb_place (
idPlace INTEGER NOT NULL,
namePlace VARCHAR(100) NOT NULL,
phonePlace VARCHAR(20),
operation VARCHAR(200),
description varchar(500),
longitude DECIMAL(8,6) NOT NULL,
latitude DECIMAL(8,6) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE evaluate_user (
idUser INTEGER NOT NULL,
idUserEvaluated INTEGER NOT NULL,
grade FLOAT NOT NULL
)ENGINE = InnoDB;

CREATE TABLE participate (
grade FLOAT NOT NULL,
idEvent INTEGER NOT NULL,
idUser INTEGER NOT NULL
)ENGINE = InnoDB;

CREATE TABLE evaluate_place (
grade FLOAT NOT NULL,
idUser INTEGER NOT NULL,
idPlace INTEGER NOT NULL
)ENGINE = InnoDB;

CREATE TABLE tb_comment (
idEvent INTEGER NOT NULL,
idUser INTEGER NOT NULL,
dsComment VARCHAR(500) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE tb_category (
idCategory INTEGER NOT NULL,
nameCategory varchar(50) NOT NULL
)ENGINE = InnoDB;

CREATE TABLE event_category(
idEvent INTEGER NOT NULL,
idCategory INTEGER NOT NULL
)ENGINE = InnoDB;


/* PRIMARY KEY */

ALTER TABLE tb_locate ADD CONSTRAINT pk_locate_longitude_latitude 
	PRIMARY KEY(longitude,latitude);

ALTER TABLE tb_event ADD CONSTRAINT pk_event_id_event 
	PRIMARY KEY(idEvent);

ALTER TABLE tb_user ADD CONSTRAINT pk_user_id_user
	PRIMARY KEY(idUser);

ALTER TABLE tb_place ADD CONSTRAINT pk_place_id_place 
	PRIMARY KEY(idPlace);

ALTER TABLE tb_category ADD CONSTRAINT pk_category_id_category
	PRIMARY KEY(idCategory);

/* INDEXES*/

CREATE INDEX index_nameEvent ON tb_event(nameEvent(10));
CREATE INDEX index_nameUser ON tb_user(nameUser(10));
CREATE INDEX index_namePlace ON tb_place(namePlace(10));

/* UNIQUE*/

ALTER TABLE evaluate_user ADD UNIQUE uk_evaluate_use (idUser, idUserEvaluated);
ALTER TABLE evaluate_place ADD UNIQUE uk_evaluate_place (idUser, idPlace);
ALTER TABLE participate ADD UNIQUE uk_participate (idUser, idEvent);
ALTER TABLE event_category ADD UNIQUE uk_category (idCategory, idEvent);
ALTER TABLE tb_user ADD UNIQUE uk_login (login);
    
/* AUTO INCREMENTS */

ALTER TABLE tb_user CHANGE COLUMN idUser idUser INTEGER NOT NULL AUTO_INCREMENT;
ALTER TABLE tb_event CHANGE COLUMN idEvent idEvent INTEGER NOT NULL AUTO_INCREMENT;
ALTER TABLE tb_place CHANGE COLUMN idPlace idPlace INTEGER NOT NULL AUTO_INCREMENT;
ALTER TABLE tb_category CHANGE COLUMN idCategory idCategory INTEGER NOT NULL AUTO_INCREMENT;

/* FOREIGN KEY*/

ALTER TABLE participate ADD CONSTRAINT fk_participate_user FOREIGN key(idUser) 
	REFERENCES tb_user(idUser) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE tb_event ADD CONSTRAINT fk_owner_event FOREIGN KEY (idOwner)
	REFERENCES tb_user(idUser) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE evaluate_user ADD CONSTRAINT fk_user_user FOREIGN key(idUser) 
	REFERENCES tb_user(idUser) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE evaluate_user ADD CONSTRAINT fk_user_user_evaluated FOREIGN key(idUserEvaluated) 
	REFERENCES tb_user(idUser) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE evaluate_place ADD CONSTRAINT fk_evaluate_place_user FOREIGN KEY(idUser) 
	REFERENCES tb_user (idUser) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE evaluate_place ADD CONSTRAINT fk_evaluate_place_place FOREIGN KEY(idPlace) 
	REFERENCES tb_place (idPlace) ON DELETE RESTRICT ON UPDATE RESTRICT;
    
ALTER TABLE participate ADD CONSTRAINT fk_participate_event FOREIGN KEY(idEvent) 
	REFERENCES tb_event (idEvent) ON DELETE RESTRICT ON UPDATE RESTRICT;
	
ALTER TABLE tb_event ADD CONSTRAINT fk_event_local FOREIGN KEY(longitude, latitude)
	REFERENCES tb_locate (longitude, latitude) ON DELETE RESTRICT ON UPDATE RESTRICT;
    
ALTER TABLE tb_place ADD CONSTRAINT fk_place_local FOREIGN KEY(longitude, latitude)
	REFERENCES tb_locate (longitude, latitude) ON DELETE RESTRICT ON UPDATE RESTRICT;
    
ALTER TABLE tb_comment ADD CONSTRAINT fk_comment_user FOREIGN KEY(idUser)
	REFERENCES tb_user(idUser) ON DELETE RESTRICT ON UPDATE RESTRICT;
    
ALTER TABLE tb_comment ADD CONSTRAINT fk_comment_event FOREIGN KEY(idEvent)
	REFERENCES tb_event(idEvent) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE event_category ADD CONSTRAINT fk_event_category_event FOREIGN KEY(idEvent)
	REFERENCES tb_event(idEvent) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE event_category ADD CONSTRAINT fk_event_category_category FOREIGN KEY(idCategory)
	REFERENCES tb_category(idCategory) ON DELETE RESTRICT ON UPDATE RESTRICT;

/* views */

CREATE VIEW vw_event AS
	SELECT tb_event.*, tb_locate.address, nameCategory,(SELECT AVG(grade)*COUNT(grade) FROM participate p WHERE p.idEvent = tb_event.idEvent GROUP BY  tb_event.idEvent) AS evaluate FROM
	tb_event INNER JOIN tb_locate ON 
		(tb_event.latitude = tb_locate.latitude AND 
        tb_event.longitude = tb_locate.longitude)
	INNER JOIN event_category on tb_event.idEvent = event_category.idEvent
	INNER JOIN tb_category on tb_category.idCategory = event_category.idCategory;
	
CREATE VIEW vw_comment AS
	SELECT vw_event.*, dsComment FROM vw_event
	LEFT JOIN tb_comment ON tb_comment.idEvent = vw_event.idEvent;

CREATE VIEW vw_place AS
	SELECT tb_place.*, tb_locate.address, AVG(grade)*COUNT(grade) evaluate FROM
	tb_place INNER JOIN tb_locate ON 
		(tb_place.latitude = tb_locate.latitude AND 
        tb_place.longitude = tb_locate.longitude)
	LEFT JOIN evaluate_place ON evaluate_place.idPlace = tb_place.idPlace
    GROUP BY tb_place.idPlace;

CREATE VIEW vw_user AS
	SELECT tb_user.*, AVG(evaluate_user.grade) evaluated from tb_user
    LEFT JOIN evaluate_user ON tb_user.idUser = evaluate_user.idUserEvaluated
    GROUP BY tb_user.idUser;

INSERT INTO tb_user(nameUser, login,passwordUser,birthDate, email)
VALUES
("Vinicius Pinheiro", "VinyPinheiro", "123456", "1995-02-14","viny-pinheiro@hotmail.com"),
("Julliana Couto", "juh", "123456", "1994-02-11","julliana.coutoalmeida@gmail.com"),
("Igor Duarte", "igodudu", "123456", "1995-11-14","igor-ribeiro@hotmail.com");

INSERT INTO tb_category(nameCategory) VALUES('Exposicao'),('Educacao'),('Cinema'),('Balada'),('Teatro'),('Museu'),('Show'),('Esporte'),('Outros');








