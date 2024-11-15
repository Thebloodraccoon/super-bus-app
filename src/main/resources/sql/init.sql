-- CREATE DATABASE superbus;
use superbus;

CREATE TABLE IF NOT EXISTS  t_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(40),
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('CLIENT', 'PARTNER') NOT NULL
);

CREATE TABLE IF NOT EXISTS  t_route (
    id INT AUTO_INCREMENT PRIMARY KEY,
    start_location VARCHAR(255) NOT NULL,
    end_location VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    partner_id INT NOT NULL,
    FOREIGN KEY (partner_id) REFERENCES t_user(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  t_stop (
    id INT AUTO_INCREMENT PRIMARY KEY,
    route_id INT NOT NULL,
    location_name VARCHAR(255) NOT NULL,
    arrival_time DATETIME,
    departure_time DATETIME,
    FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS t_ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    route_id INT NOT NULL,
    purchase_date DATETIME,
    status VARCHAR(50) DEFAULT 'куплений',
    seats INT NOT NULL,
    duration_hours INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES t_user(id) ON DELETE CASCADE,
    FOREIGN KEY (route_id) REFERENCES t_route(id) ON DELETE CASCADE
);
