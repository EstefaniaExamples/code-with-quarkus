CREATE TABLE book (id SERIAL PRIMARY KEY, title TEXT NOT NULL, isbn_13 TEXT NOT NULL, author TEXT, description TEXT);

INSERT INTO Book(id, isbn_13, title, author, description)
VALUES ( 997, '9781980399025', 'Understanding Bean Validation', 'Antonio Goncalves', 'In this fascicle will you will learn Bean Validation and use its different APIs to apply constraints on a bean, validate all sorts of constraints and write your own constraints');

INSERT INTO Book(id, isbn_13, title, author, description)
VALUES ( 998, '9781093918977', 'Understanding JPA', 'Antonio Goncalves', 'In this fascicle, you will learn Java Persistence API, its annotations for mapping entities, as well as the Java Persistence Query Language and entity life cycle');
