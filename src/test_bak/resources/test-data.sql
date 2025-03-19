-- Testdata til unittests

-- Ryd
DELETE FROM Anmeldelser;
DELETE FROM Brugere;
DELETE FROM Seværdigheder;

-- test data
INSERT INTO Seværdigheder (id, navn, lokation, beskrivelse, bedømmelse) VALUES
(1, 'Tivoli', 'København', 'Historisk forlystelsespark i hjertet af København', 4.70),
(2, 'Den Lille Havfrue', 'København', 'Ikonisk statue ved Langelinie i København', 4.20);

INSERT INTO Brugere (id, brugernavn, email, adgangskode) VALUES
(1, 'testbruger1', 'test1@example.com', 'password123'),
(2, 'testbruger2', 'test2@example.com', 'password123');

INSERT INTO Anmeldelser (id, bruger_id, seværdighed_id, bedømmelse, kommentar) VALUES
(1, 1, 1, 5, 'Fantastisk oplevelse'),
(2, 2, 1, 4, 'God oplevelse, men dyrt'),
(3, 1, 2, 3, 'Mindre end forventet');