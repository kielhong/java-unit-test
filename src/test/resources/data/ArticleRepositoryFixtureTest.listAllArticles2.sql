INSERT INTO board (id, name) VALUES (5, 'board');

INSERT INTO article (board_id, subject, content, username, created_At) VALUES
    (5, 'subject1', 'content1', 'user', now()),
    (5, 'subject2', 'content2', 'user', now()),
    (5, 'subject3', 'content3', 'user', now());

