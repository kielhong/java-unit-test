INSERT INTO board (id, name) VALUES (5, 'board');

INSERT INTO article (id, board_id, subject, content, username, created_At) VALUES
    (1, 5, 'subject1', 'content2', 'user', now()),
    (2, 5, 'subject2', 'content2', 'user', now());

