USE superbus;

-- Додавання даних у таблицю t_user
INSERT INTO t_user (name, email, phone, passwordHash, role)
VALUES
('Іван Петренко', 'ivan.petrenko@example.com', '+380631234567', 'hashedpassword1', 'CLIENT'),
('Олена Іванова', 'olena.ivanova@example.com', '+380971234567', 'hashedpassword2', 'CLIENT'),
('Транспортна Компанія 1', 'company1@example.com', '+380501234567', 'hashedpassword3', 'PARTNER'),
('Транспортна Компанія 2', 'company2@example.com', '+380671234567', 'hashedpassword4', 'PARTNER');

-- Додавання даних у таблицю t_route
INSERT INTO t_route (startLocation, endLocation, description, price, partner_id)
VALUES
('Київ', 'Львів', 'Швидкісне перевезення між Києвом та Львовом', 500.00, 3),
('Харків', 'Одеса', 'Нічний рейс з Харкова до Одеси', 800.00, 4);

-- Додавання даних у таблицю t_stop
INSERT INTO t_stop (route_id, locationName, arrivalTime, departureTime)
VALUES
(1, 'Житомир', '2024-11-15 10:00:00', '2024-11-15 10:15:00'),
(1, 'Тернопіль', '2024-11-15 14:00:00', '2024-11-15 14:15:00'),
(2, 'Дніпро', '2024-11-16 22:00:00', '2024-11-16 22:15:00');

-- Додавання даних у таблицю t_ticket
INSERT INTO t_ticket (user_id, route_id, purchaseDate, status, seats, durationHours)
VALUES
(1, 1, '2024-11-12 08:00:00', 'куплений', 1, 5),
(2, 2, '2024-11-12 09:00:00', 'куплений', 2, 10);

-- Додавання даних у таблицю t_payment
INSERT INTO t_payment (ticket_id, amount, paymentMethod, paymentDate, status)
VALUES
(1, 500.00, 'картка', '2024-11-12 08:05:00', 'успішний'),
(2, 1600.00, 'електронний гаманець', '2024-11-12 09:05:00', 'успішний');

-- Додавання даних у таблицю t_schedule
INSERT INTO t_schedule (route_id, departureTime, arrivalTime)
VALUES
(1, '2024-11-15 08:00:00', '2024-11-15 18:00:00'),
(2, '2024-11-16 20:00:00', '2024-11-17 06:00:00');
