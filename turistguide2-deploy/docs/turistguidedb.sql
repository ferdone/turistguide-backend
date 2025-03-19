CREATE DATABASE IF NOT EXISTS turistguidedb;
USE turistguidedb;

DROP TABLE IF EXISTS Anmeldelser;
DROP TABLE IF EXISTS Brugere;
DROP TABLE IF EXISTS Seværdigheder;

CREATE TABLE Seværdigheder (
    id INT AUTO_INCREMENT PRIMARY KEY,
    navn VARCHAR(255) NOT NULL,
    lokation VARCHAR(255) NOT NULL,
    beskrivelse TEXT NOT NULL,
    bedømmelse DECIMAL(3,2) DEFAULT 0.00
);

CREATE TABLE Brugere (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brugernavn VARCHAR(100) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    adgangskode VARCHAR(255) NOT NULL
);

CREATE TABLE Anmeldelser (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bruger_id INT,
    seværdighed_id INT,
    bedømmelse INT CHECK (bedømmelse BETWEEN 1 AND 5),
    kommentar TEXT,
    oprettet_dato TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (bruger_id) REFERENCES Brugere(id) ON DELETE CASCADE,
    FOREIGN KEY (seværdighed_id) REFERENCES Seværdigheder(id) ON DELETE CASCADE
);

INSERT INTO Seværdigheder (navn, lokation, beskrivelse, bedømmelse)
VALUES
('Rosenborg Slot', 'København', 'Skatte og kronjuveler.', 4.80),
('Den Lille Havfrue', 'København', 'En ikonisk statue ved havnen.', 4.20),
('Rundetårn', 'København', 'Et gammelt astronomisk observatorium.', 4.60),
('Tivoli', 'København', 'Historisk forlystelsespark i hjertet af København', 4.70),
('Legoland', 'Billund', 'Familievenlig temapark baseret på LEGO klodser', 4.50);

INSERT INTO Brugere (brugernavn, email, adgangskode)
VALUES
('voldemord_83', 'minmail1@live.dk', 'password123'),
('harry_p', 'minmail2@live.dk', 'password123'),
('johnsn', 'minmail3@live.dk', 'password123');

INSERT INTO Anmeldelser (bruger_id, seværdighed_id, bedømmelse, kommentar)
VALUES
(1, 1, 5, 'Smukt skråt slot.'),
(3, 3, 5, 'Havde forventet det var større'),
(2, 2, 4, 'Kunne godt lide udsigten');
