-- CREATE DATABASE superbus;
use superbus;



CREATE TABLE IF NOT EXISTS  t_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(40),
    passwordHash VARCHAR(255) NOT NULL,
    role ENUM('CLIENT', 'PARTNER') NOT NULL
);

CREATE TABLE IF NOT EXISTS  t_route (
    id INT AUTO_INCREMENT PRIMARY KEY,
    startLocation VARCHAR(255) NOT NULL,
    endLocation VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    partner_id INT NOT NULL,
    FOREIGN KEY (partner_id) REFERENCES t_user(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  t_stop (
    id INT AUTO_INCREMENT PRIMARY KEY,
    route_id INT NOT NULL,
    locationName VARCHAR(255) NOT NULL,
    arrivalTime TIMESTAMP,
    departureTime TIMESTAMP,
    FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    route_id INT NOT NULL,
    purchaseDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'куплений',
    seats INT NOT NULL,
    durationHours INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  t_payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    paymentMethod VARCHAR(50) NOT NULL,
    paymentDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'успішний',
    FOREIGN KEY (ticket_id) REFERENCES t_ticket(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  t_schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    route_id INT NOT NULL,
    departureTime TIMESTAMP NOT NULL,
    arrivalTime TIMESTAMP NOT NULL,
    FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE
);

