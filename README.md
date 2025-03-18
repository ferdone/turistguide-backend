# Turistguide Projekt

Mit projekt til databaser og API programmering. Lavet af [dit navn/studienummer].

## Om projektet

Dette er en simpel API til at håndtere turistattraktioner i Danmark. Jeg har brugt Spring Boot og MySQL som database. Systemet kan vise info om turistattraktioner, og brugere kan oprette sig, skrive anmeldelser og give bedømmelser.

## Projektstruktur

- `/src/main/java`: Java kode
- `/src/main/resources`: Opsætning og statiske filer
- `/src/test`: Mine tests (ikke 100% færdige)
- `/database`: SQL scripts til oprettelse af database
- `/docs`: Min dokumentation, bl.a. ER-diagram

## ER-Diagram

Se [ER-Diagram](docs/ER_Diagram.md) for beskrivelse af databasen.

## API Endpoints

Her er endpoints man kan bruge:

- `GET /api/attraktioner`: Henter alle attraktioner
- `GET /api/attraktioner/{navn}`: Henter en bestemt attraktion
- `POST /api/attraktioner`: Opretter ny attraktion
- `PUT /api/attraktioner/{navn}`: Opdaterer en attraktion
- `DELETE /api/attraktioner/{navn}`: Sletter en attraktion

## Sådan kører du koden lokalt

### Du skal bruge

- Java 11 eller nyere
- Maven
- MySQL 8 eller nyere

### Database opsætning

1. Lav en MySQL database:

```sql
CREATE DATABASE turistguidedb;
```

2. Kør SQL scriptet:

```bash
mysql -u root -p turistguidedb < database/turistguidedb.sql
```

### Start applikationen

1. Byg projektet:

```bash
mvn clean install
```

2. Kør applikationen:

```bash
mvn spring-boot:run
```

Programmet kører nu på http://localhost:8080/api

## Ideer til forbedringer

(har ikke haft tid til at implementere)

- Login system med password kryptering
- Mere validering af input data
- Forbedre fejlhåndtering
- Tilføje flere endpoints for anmeldelser
- Lave et UI med React eller Vue

## Azure deployment

Se [Azure Deployment Guide](docs/azure-deployment.md) for hvordan man deployer til Azure.
