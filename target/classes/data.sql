INSERT INTO SEVAERDIGHED (id, navn, lokation, beskrivelse, bedoemmelse) VALUES
(1, 'Rosenborg Slot', 'København', 'Skatte og kronjuveler.', 4.80),
(2, 'Den Lille Havfrue', 'København', 'En ikonisk statue ved havnen.', 4.20),
(3, 'Rundetårn', 'København', 'Et gammelt astronomisk observatorium.', 4.60),
(4, 'Tivoli', 'København', 'Historisk forlystelsespark i hjertet af København', 4.70),
(5, 'Legoland', 'Billund', 'Familievenlig temapark baseret på LEGO klodser', 4.50);

INSERT INTO BRUGER (id, brugernavn, email, adgangskode) VALUES
(1, 'voldemord_83', 'minmail1@live.dk', 'password123'),
(2, 'harry_p', 'minmail2@live.dk', 'password123'),
(3, 'johnsn', 'minmail3@live.dk', 'password123');

INSERT INTO ANMELDELSE (bruger_id, sevaerdighed_id, bedoemmelse, kommentar) VALUES
(1, 1, 5, 'Smukt slot med masser af historie.'),
(3, 3, 5, 'Havde forventet det var større'),
(2, 2, 4, 'Kunne godt lide udsigten'); 