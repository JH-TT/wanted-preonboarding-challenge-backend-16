INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve, created_at)
VALUES (uuid_to_bin(uuid(), 1), '레베카', 100000, 1, 0, '2024-01-20 19:30:00', DEFAULT, now());
INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve, created_at)
VALUES (uuid_to_bin(uuid(), 1), '레베카', 100000, 2, 0, '2024-01-20 19:30:00', DEFAULT, now());
INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve, created_at)
VALUES (uuid_to_bin(uuid(), 1), '레베카', 100000, 3, 0, '2024-01-20 19:30:00', DEFAULT, now());
INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve, created_at)
VALUES (uuid_to_bin(uuid(), 1), '캣츠', 150000, 1, 0, '2024-01-20 19:30:00', DEFAULT, now());
INSERT INTO `performance` (id, name, price, round, type, start_date, is_reserve, created_at)
VALUES (uuid_to_bin(uuid(), 1), '캣츠', 150000, 2, 0, '2024-01-20 19:30:00', DEFAULT, now());

INSERT INTO `performance_seat_info` (id, performance_id, `round`, gate, line, seat, is_reserve)
VALUES
 (DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 1, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 2, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 3, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'A', 4, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'B', 1, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'B', 2, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'B', 3, 'enable')
,(DEFAULT, (SELECT id FROM performance limit 1), 1, 1, 'B', 4, 'enable');

commit;