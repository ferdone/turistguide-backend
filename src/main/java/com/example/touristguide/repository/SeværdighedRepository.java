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
            System.out.println("fejl: " + e.getMessage());
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
        }
        
        return Optional.empty();
    }

    public Seværdighed save(Seværdighed sev) {
        return sev.getId() == null ? insert(sev) : update(sev);
    }

    private Seværdighed insert(Seværdighed sev) {
        String sql = "INSERT INTO Seværdigheder (navn, lokation, beskrivelse, bedømmelse) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, sev.getNavn());
            stmt.setString(2, sev.getLokation());
            stmt.setString(3, sev.getBeskrivelse());
            stmt.setBigDecimal(4, sev.getBedømmelse());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        sev.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("fejl ved insert: " + e.getMessage());
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
            System.out.println("fejl: " + e.getMessage());
        }
        
        return sev;
    }

    public void deleteByNavn(String navn) {
        String sql = "DELETE FROM Seværdigheder WHERE navn = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, navn);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("fejl: " + e.getMessage());
        }
    }
} 