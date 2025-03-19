DROP DATABASE IF EXISTS turistguidedb;
CREATE DATABASE turistguidedb;
USE turistguidedb;

CREATE TABLE byer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    navn VARCHAR(100) NOT NULL,
    beskrivelse TEXT,
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    oprettet_dato TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sevaerdigheder (
    id INT AUTO_INCREMENT PRIMARY KEY,
    by_id INT,
    navn VARCHAR(100) NOT NULL,
    beskrivelse TEXT,
    adresse VARCHAR(255),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    oprettet_dato TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (by_id) REFERENCES byer(id)
);

CREATE TABLE billeder (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sevaerdighed_id INT,
    url VARCHAR(255) NOT NULL,
    beskrivelse VARCHAR(255),
    oprettet_dato TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sevaerdighed_id) REFERENCES sevaerdigheder(id)
);

INSERT INTO byer (navn, beskrivelse, latitude, longitude) VALUES
('København', 'Danmarks hovedstad og største by', 55.676098, 12.568337),
('Aarhus', 'Danmarks anden største by', 56.156635, 10.210866),
('Odense', 'H.C. Andersens fødeby', 55.403756, 10.402370);

INSERT INTO sevaerdigheder (by_id, navn, beskrivelse, adresse, latitude, longitude) VALUES
(1, 'Tivoli', 'Berømt forlystelsespark i hjertet af København', 'Vesterbrogade 3', 55.673752, 12.567834),
(1, 'Den Lille Havfrue', 'Ikonisk statue ved Langelinie', 'Langelinie', 55.692891, 12.599278),
(2, 'ARoS', 'Kunstmuseum med regnbue panorama', 'Aros Allé 2', 56.153461, 10.199432),
(3, 'H.C. Andersens Hus', 'Museum dedikeret til den berømte forfatter', 'Bangs Boder 29', 55.395843, 10.388123); 