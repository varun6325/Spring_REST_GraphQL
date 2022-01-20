
DROP SCHEMA IF EXISTS forgraphql;

CREATE SCHEMA forgraphql;
USE forgraphql;


CREATE TABLE author(
  id  Int NOT NULL, 
  name         VARCHAR(255) NOT NULL,
  email        VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);


CREATE TABLE post(
  id  Int NOT NULL, 
  title         VARCHAR(255) NOT NULL,
  description        VARCHAR(255) NOT NULL,
  category        VARCHAR(255) NOT NULL,
  author_id        VARCHAR(255) NOT NULL,
  PRIMARY KEY(id)
);
