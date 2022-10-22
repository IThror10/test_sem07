INSERT INTO app.users VALUES(0, '1980-01-01 00:00:00', 0, 'admin', 'password', 'admin@gmail.com');


INSERT INTO app.events VALUES
    (0, '1980-01-01 00:00:12', 2, '{"Term" : "NewTerm", "Description" : "Some text"}'),
    (0, '1980-01-01 00:00:25', 3, '{"Term" : "NewTerm", "Tag" : "NewTag"}'),
    (0, '1980-01-01 03:01:12', 4, '{"Term" : "NewTerm", "Book" : {"Name" : "NewBook", "Type" : "NewType", "Year" : 2000, "Authors" : ["qwerty"]}}'),
    (0, '1980-01-02 00:00:15', 6, '{"Term" : "NewTerm", "Tag" : "NewTag", "Mark" : 5}'),
    (0, '1980-01-03 12:48:24', 5, '{"Term" : "NewTerm", "Book" : {"Name" : "NewBook", "Type" : "NewType", "Year" : 2000, "Authors" : ["qwerty"]}, "Mark" : 5, "Comment" : "Some text"}');
