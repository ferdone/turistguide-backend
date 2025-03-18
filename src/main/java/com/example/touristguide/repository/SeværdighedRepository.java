package com.example.touristguide.repository;

import com.example.touristguide.model.Seværdighed;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SeværdighedRepository {

    private final DataSource dataSource;

    public SeværdighedRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Seværdighed> findAll() {
        List<Seværdighed> seværdigheder = new ArrayList<>();
        String sql = "SELECT id, navn, lokation, beskrivelse, bedømmelse FROM Seværdigheder";
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Seværdighed s = new Seværdighed();
                s.setId(rs.getLong("id"));
                s.setNavn(rs.getString("navn"));
                s.setLokation(rs.getString("lokation"));
                s.setBeskrivelse(rs.getString("beskrivelse"));
                s.setBedømmelse(rs.getBigDecimal("bedømmelse"));
                seværdigheder.add(s);
            }
        } catch (SQLException e) {
            System.out.println("øv, kunne ikke hente: " + e.getMessage());
            e.printStackTrace();
        }
        
        return seværdigheder;
    }

    public Optional<Seværdighed> findByNavn(String navn) {
        String sql = "SELECT id, navn, lokation, beskrivelse, bedømmelse FROM Seværdigheder WHERE navn = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, navn);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Seværdighed s = new Seværdighed();
                    s.setId(rs.getLong("id"));
                    s.setNavn(rs.getString("navn"));
                    s.setLokation(rs.getString("lokation"));
                    s.setBeskrivelse(rs.getString("beskrivelse"));
                    s.setBedømmelse(rs.getBigDecimal("bedømmelse"));
                    return Optional.of(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("fejl ved søgning efter " + navn + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    public Optional<Seværdighed> getById(Long id) {
        String sql = "SELECT id, navn, lokation, beskrivelse, bedømmelse FROM Seværdigheder WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Seværdighed s = new Seværdighed();
                s.setId(rs.getLong("id"));
                s.setNavn( rs.getString("navn") );
                s.setLokation(rs.getString("lokation"));
                s.setBeskrivelse(rs.getString("beskrivelse"));
                s.setBedømmelse(rs.getBigDecimal("bedømmelse"));
                rs.close();
                return Optional.of(s);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("kunne ikke finde id " + id);
            e.printStackTrace();
        }
        
        return Optional.empty();
    }

    public Seværdighed save(Seværdighed sev) {
        if (sev.getId() != null && getById(sev.getId()).isPresent()) {
            return update(sev);
        } else {
            return insert(sev);
        }
    }

    private Seværdighed insert(Seværdighed sev) {
        String sql = "INSERT INTO Seværdigheder (navn, lokation, beskrivelse, bedømmelse) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, sev.getNavn());
            stmt.setString(2, sev.getLokation());
            stmt.setString(3, sev.getBeskrivelse());
            
            if (sev.getBedømmelse() != null) {
                stmt.setBigDecimal(4, sev.getBedømmelse());
            } else {
                stmt.setBigDecimal(4, BigDecimal.ZERO);
            }
            
            int påvirkede = stmt.executeUpdate();
            
            if (påvirkede == 0) {
                throw new SQLException("kunne ikke oprette seværdighed, ingen rækker påvirket");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    sev.setId(id);
                }
            }
        } catch (SQLException e) {
            System.out.println("fejl ved indsættelse: " + e.getMessage());
            e.printStackTrace();
        }
        
        return sev;
    }

    private Seværdighed update(Seværdighed sev) {
        String sql = "UPDATE Seværdigheder SET navn = ?, lokation = ?, beskrivelse = ?, bedømmelse = ? WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, sev.getNavn());
            stmt.setString(2, sev.getLokation());
            stmt.setString(3, sev.getBeskrivelse());
            stmt.setBigDecimal(4, sev.getBedømmelse());
            stmt.setLong(5, sev.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("fejl ved opdatering: " + e.getMessage());
            e.printStackTrace();
        }
        
        return sev;
    }

    public void deleteByNavn(String navn) {
        String sql = "DELETE FROM Seværdigheder WHERE navn = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, navn);
            int rowsDeleted = stmt.executeUpdate();
            System.out.println("slettede " + rowsDeleted + " rækker");
        } catch (SQLException e) {
            System.out.println("fejl ved sletning: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 