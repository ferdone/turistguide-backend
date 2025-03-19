-- Use this script if the database tables don't exist.
-- When using 'validate' as the ddl-auto setting, Hibernate will check
-- if the tables match the entity structure without creating them.

-- This is the schema that matches our entity model

-- Seværdigheder table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Seværdigheder')
BEGIN
    CREATE TABLE Seværdigheder (
        id INT IDENTITY(1,1) PRIMARY KEY,
        navn NVARCHAR(255) NOT NULL,
        lokation NVARCHAR(255) NOT NULL,
        beskrivelse NVARCHAR(MAX) NOT NULL,
        bedømmelse DECIMAL(3,2) DEFAULT 0.00
    );
END

-- Brugere table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Brugere')
BEGIN
    CREATE TABLE Brugere (
        id INT IDENTITY(1,1) PRIMARY KEY,
        brugernavn NVARCHAR(100) NOT NULL UNIQUE,
        email NVARCHAR(255) NOT NULL UNIQUE,
        adgangskode NVARCHAR(255) NOT NULL
    );
END

-- Anmeldelser table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Anmeldelser')
BEGIN
    CREATE TABLE Anmeldelser (
        id INT IDENTITY(1,1) PRIMARY KEY,
        bruger_id INT,
        seværdighed_id INT,
        bedømmelse INT CHECK (bedømmelse BETWEEN 1 AND 5),
        kommentar NVARCHAR(MAX),
        oprettet_dato DATETIME DEFAULT GETDATE(),
        CONSTRAINT FK_Anmeldelser_Brugere FOREIGN KEY (bruger_id) REFERENCES Brugere(id) ON DELETE CASCADE,
        CONSTRAINT FK_Anmeldelser_Seværdigheder FOREIGN KEY (seværdighed_id) REFERENCES Seværdigheder(id) ON DELETE CASCADE
    );
END 