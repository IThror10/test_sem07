INSERT INTO data.users values (1);
INSERT INTO data.users values (2);

-- Terms
INSERT INTO data.terms values ('OOP', 'Object Oriented Programming');
INSERT INTO data.terms values ('Singleton', 'Single object pattern');

-- Terms Tags Connection
INSERT INTO data.terms_tags values('OOP', 'IT');
INSERT INTO data.terms_tags values('OOP', 'Medicine');
INSERT INTO data.terms_tags values('OOP', 'Pattern');
INSERT INTO data.terms_tags values('Singleton', 'Pattern');
INSERT INTO data.terms_tags values('Singleton', 'AntiPattern');

-- Books
INSERT INTO data.lit values('Go4', 2010, 'book', '["ABC", "DEF"]');
INSERT INTO data.lit values('White Fang', 1906, 'Novel', '["London J."]');

-- Terms Lit Connection
INSERT INTO data.terms_lit values('Singleton', 'Go4');
INSERT INTO data.terms_lit values('OOP', 'Go4');

-- Terms Tags Rates
INSERT INTO data.term_tag_rates values(1, 'OOP', 'IT', 5);
INSERT INTO data.term_tag_rates values(1, 'OOP', 'Pattern', 4);
INSERT INTO data.term_tag_rates values(1, 'Singleton', 'Pattern', 4);
INSERT INTO data.term_tag_rates values(1, 'Singleton', 'AntiPattern', 5);
INSERT INTO data.term_tag_rates values(2, 'OOP', 'Medicine', 1);
INSERT INTO data.term_tag_rates values(2, 'OOP', 'IT', 3);

INSERT INTO data.term_lit_rates values(1, 'OOP', 'Go4', 4);
INSERT INTO data.term_lit_rates values(2, 'Singleton', 'Go4', 3);
