package com.example.touristguide.repository;

import com.example.touristguide.model.Bruger;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BrugerRepository {

    private final DataSource dataSource;

    public BrugerRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Bruger> findAll() {
        List<Bruger> brugere = new ArrayList<>();
        String sql = "SELECT id, brugernavn, email, adgangskode FROM Brugere";
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Bruger bruger = new Bruger();
                bruger.setId(rs.getLong("id"));
                bruger.setBrugernavn(rs.getString("brugernavn"));
                bruger.setEmail(rs.getString("email"));
                bruger.setAdgangskode(rs.getString("adgangskode"));
                brugere.add(bruger);
            }
        } catch (SQLException e) {
            // fejl ved hentning
            System.out.println("ups: " + e.getMessage());
            e.printStackTrace();
        }
        
        return brugere;
    }

    public Optional<Bruger> findById(Long id) {
        String sql = "SELECT id, brugernavn, email, adgangskode FROM Brugere WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bruger bruger = new Bruger();
                    bruger.setId(rs.getLong("id"));
                    bruger.setBrugernavn(rs.getString("brugernavn"));
                    bruger.setEmail(rs.getString("email"));
                    bruger.setAdgangskode(rs.getString("adgangskode"));
                    return Optional.of(bruger);
                }
            }
        } catch (SQLException e) {
            // kunne ikke finde
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    public Optional<Bruger> findByBrugernavn(String brugernavn) {
        String sql = "SELECT id, brugernavn, email, adgangskode FROM Brugere WHERE brugernavn = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, brugernavn);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Bruger bruger = new Bruger();
                    bruger.setId(rs.getLong("id"));
                    bruger.setBrugernavn(rs.getString("brugernavn"));
                    bruger.setEmail(rs.getString("email"));
                    bruger.setAdgangskode(rs.getString("adgangskode"));
                    return Optional.of(bruger);
                }
            }
        } catch (SQLException e) {
            // fejl ved søgning
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    public Bruger save(Bruger bruger) {
        if (bruger.getId() != null && findById(bruger.getId()).isPresent()) {
            return update(bruger);
        } else {
            return insert(bruger);
        }
    }

    private Bruger insert(Bruger bruger) {
        String sql = "INSERT INTO Brugere (brugernavn, email, adgangskode) VALUES (?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, bruger.getBrugernavn());
            stmt.setString(2, bruger.getEmail());
            stmt.setString(3, bruger.getAdgangskode());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("kunne ikke indsætte bruger");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    bruger.setId(id);
                }
            }
        } catch (SQLException e) {
            // fejl ved oprettelse
            e.printStackTrace();
        }
        
        return bruger;
    }

    private Bruger update(Bruger bruger) {
        String sql = "UPDATE Brugere SET brugernavn = ?, email = ?, adgangskode = ? WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, bruger.getBrugernavn());
            stmt.setString(2, bruger.getEmail());
            stmt.setString(3, bruger.getAdgangskode());
            stmt.setLong(4, bruger.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            // fejl ved opdatering
            e.printStackTrace();
        }
        
        return bruger;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM Brugere WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            // fejl ved sletning
            e.printStackTrace();
        }
    }
} 