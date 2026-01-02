Drop database icinemadb;
CREATE DATABASE icinemadb;
Use icinemadb;


CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS movies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    image_url VARCHAR(500),
    genre VARCHAR(50) NOT NULL,
    language VARCHAR(50) NOT NULL,
    description TEXT,
    duration_in_minutes INT,
    rating DOUBLE,
    censor_rating VARCHAR(10),
    total_votes INT DEFAULT 0
);

CREATE TABLE IF NOT EXISTS theatres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    city VARCHAR(100) DEFAULT 'Bengaluru'
);

CREATE TABLE IF NOT EXISTS shows (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_id BIGINT NOT NULL,
    theatre_id BIGINT NOT NULL,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (theatre_id) REFERENCES theatres(id)
);

CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    show_id BIGINT NOT NULL,
    user_id BIGINT,
    user_email VARCHAR(255) NOT NULL,
    booking_time DATETIME NOT NULL,
    total_amount DOUBLE NOT NULL,
    convenience_fee DOUBLE,
    gst DOUBLE,
    FOREIGN KEY (show_id) REFERENCES shows(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS seats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    show_id BIGINT NOT NULL,
    booking_id BIGINT,
    seat_number VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL, -- Enum: AVAILABLE, BOOKED
    price DOUBLE NOT NULL,
    version BIGINT DEFAULT 0,
    FOREIGN KEY (show_id) REFERENCES shows(id),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL UNIQUE,
    amount DOUBLE NOT NULL,
    discount_amount DOUBLE,
    payment_time DATETIME NOT NULL,
    card_type VARCHAR(20) NOT NULL, -- Enum: DEBIT, CREDIT
    payment_status VARCHAR(20),
    masked_card_number VARCHAR(20),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

CREATE TABLE IF NOT EXISTS ratings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    rating INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    UNIQUE (user_id, movie_id)
);