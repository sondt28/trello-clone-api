INSERT INTO "user" (email, full_name, password, role)
VALUES ('user1@example.com', 'User One', 'password1', 'ROLE_OWNER'),
       ('user2@example.com', 'User Two', 'password2', 'ROLE_OWNER'),
       ('user3@example.com', 'User Three', 'password3', 'ROLE_OWNER'),
       ('user4@example.com', 'User Four', 'password4', 'ROLE_OWNER');

-- INSERT INTO project (hex_color, is_public, name, order, owner_user_id)
-- VALUES ('#808080', 1, 'Project One', 1, 1),
--        ('#336699', 0, 'Project Two', 2, 1),
--        ('#990000', 1, 'Project Three', 3, 1);

-- INSERT INTO section (name, order, project_id)
-- VALUES ('Section A', 6, 1),
--        ('Section B', 5, 1),
--        ('Section X', 4, 1),
--        ('Section Y', 3, 1),
--        ('Section P', 1, 1),
--        ('Section Q', 2, 1);
--
-- INSERT INTO project_user (project_id, user_id)
-- VALUES (1, 1),
--        (1, 2),
--        (2, 2),
--        (3, 2);