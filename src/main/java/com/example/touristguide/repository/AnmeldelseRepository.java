package com.example.touristguide.repository;

import com.example.touristguide.model.Anmeldelse;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnmeldelseRepository {

    private final DataSource dataSource;

    public AnmeldelseRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // get all reviews
    public List<Anmeldelse> findAll() {
        List<Anmeldelse> anmeldelser = new ArrayList<>();
        String sql = "SELECT id, bruger_id, seværdighed_id, bedømmelse, kommentar, oprettet_dato FROM Anmeldelser";
        
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Anmeldelse anm = new Anmeldelse();
                anm.setId(rs.getLong("id"));
                anm.setUserId(rs.getLong("bruger_id"));
                anm.setSeværdighedId(rs.getLong("seværdighed_id"));
                anm.setBedømmelse(rs.getInt("bedømmelse"));
                anm.setKommentar(rs.getString("kommentar"));
                anm.setOprettetDato(rs.getTimestamp("oprettet_dato").toLocalDateTime());
                anmeldelser.add(anm);
            }
        } catch (SQLException e) {
            System.out.println("fejl: " + e.getMessage());
        }
        
        return anmeldelser;
    }

    public Optional<Anmeldelse> findById(Long id) {
        String sql = "SELECT id, bruger_id, seværdighed_id, bedømmelse, kommentar, oprettet_dato FROM Anmeldelser WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Anmeldelse anm = new Anmeldelse();
                    anm.setId(rs.getLong("id"));
                    anm.setUserId(rs.getLong("bruger_id"));
                    anm.setSeværdighedId(rs.getLong("seværdighed_id"));
                    anm.setBedømmelse(rs.getInt("bedømmelse"));
                    anm.setKommentar(rs.getString("kommentar"));
                    anm.setOprettetDato(rs.getTimestamp("oprettet_dato").toLocalDateTime());
                    return Optional.of(anm);
                }
            }
        } catch (SQLException e) {
            System.out.println("fejl: " + e.getMessage());
        }
        
        return Optional.empty();
    }

    public List<Anmeldelse> findByBrugerId(Long brugerId) {
        List<Anmeldelse> anmeldelser = new ArrayList<>();
        String sql = "SELECT id, bruger_id, seværdighed_id, bedømmelse, kommentar, oprettet_dato FROM Anmeldelser WHERE bruger_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, brugerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Anmeldelse anm = new Anmeldelse();
                    anm.setId(rs.getLong("id"));
                    anm.setUserId(rs.getLong("bruger_id"));
                    anm.setSeværdighedId(rs.getLong("seværdighed_id"));
                    anm.setBedømmelse(rs.getInt("bedømmelse"));
                    anm.setKommentar(rs.getString("kommentar"));
                    anm.setOprettetDato(rs.getTimestamp("oprettet_dato").toLocalDateTime());
                    anmeldelser.add(anm);
                }
            }
        } catch (SQLException e) {
            System.out.println("noget gik galt: " + e.getMessage());
        }
        
        return anmeldelser;
    }

    //finder reviews by attraction ID
    public List<Anmeldelse> findBySeværdighedId(Long seværdighedId) {
        // samme som ovenfor, bare med et andet id
        List<Anmeldelse> anmeldelser = new ArrayList<>();
        String sql = "SELECT id, bruger_id, seværdighed_id, bedømmelse, kommentar, oprettet_dato FROM Anmeldelser WHERE seværdighed_id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, seværdighedId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Anmeldelse anm = new Anmeldelse();
                    anm.setId(rs.getLong("id"));
                    anm.setUserId(rs.getLong("bruger_id"));
                    anm.setSeværdighedId(rs.getLong("seværdighed_id"));
                    anm.setBedømmelse(rs.getInt("bedømmelse"));
                    anm.setKommentar(rs.getString("kommentar"));
                    anm.setOprettetDato(rs.getTimestamp("oprettet_dato").toLocalDateTime());
                    anmeldelser.add(anm);
                }
            }
        } catch (SQLException ex) {
            // different error handling style here
            System.out.println("ERROR: " + ex);
            return new ArrayList<>(); // return empty list instead of null
        }
        
        return anmeldelser;
    }

    public Anmeldelse save(Anmeldelse anmeldelse) {
        // tjekker om vi har en id, hvis ja så opdater, hvis nej så indsæt
        if (anmeldelse.getId() != null && findById(anmeldelse.getId()).isPresent()) {
            return update(anmeldelse);
        } else {
            return insert(anmeldelse);
        }
    }

    private Anmeldelse insert(Anmeldelse anm) {
        String sql = "INSERT INTO Anmeldelser (bruger_id, seværdighed_id, bedømmelse, kommentar) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setLong(1, anm.getUserId());
            stmt.setLong(2, anm.getSeværdighedId());
            stmt.setInt(3, anm.getBedømmelse());
            stmt.setString(4, anm.getKommentar());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                // ingen rækker blev oprettet :(
                throw new SQLException("kunne ikke oprette anmeldelse");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long id = generatedKeys.getLong(1);
                    anm.setId(id);
                }
            }
            
            // opdater gennemsnitsrating for seværdigheden
            updateAvgRating(anm.getSeværdighedId());
            
        } catch (SQLException e) {
            // fejl ved gemme
            System.out.println("fejl ved indsættelse: " + e.getMessage());
            e.printStackTrace();
        }
        
        return anm;
    }

    private Anmeldelse update(Anmeldelse anm) {
        // opdater eksisterende anmeldelse
        String sql = "UPDATE Anmeldelser SET bruger_id = ?, seværdighed_id = ?, bedømmelse = ?, kommentar = ? WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, anm.getUserId());
            stmt.setLong(2, anm.getSeværdighedId());
            stmt.setInt(3, anm.getBedømmelse());
            stmt.setString(4, anm.getKommentar());
            stmt.setLong(5, anm.getId());
            
            stmt.executeUpdate();
            
            // opdater rating efter ændring
            updateAvgRating(anm.getSeværdighedId());
            
        } catch (SQLException e) {
            System.out.println("databasefejl: " + e.getMessage());
            e.printStackTrace();
        }
        
        return anm;
    }

    // delete a review by ID
    public void delete(Long id) {
        // find først anmeldelsen, så vi kender seværdighedsId
        Optional<Anmeldelse> anmeldelse = findById(id);
        if (!anmeldelse.isPresent()) {
            System.out.println("forsøger at slette anmeldelse med id=" + id + " men den findes ikke");
            return;
        }
        
        Long seværdighedId = anmeldelse.get().getSeværdighedId();
        
        String sql = "DELETE FROM Anmeldelser WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
            
            // opdater når en anmeldelse er slettet
            updateAvgRating(seværdighedId);
            
        } catch (SQLException e) {
            System.out.println("fejl ved sletning: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //calculate average
    private void updateAvgRating(Long seværdighedId) {
        String sqlAvg = "SELECT AVG(bedømmelse) as avg_rating FROM Anmeldelser WHERE seværdighed_id = ?";
        String sqlUpdate = "UPDATE Seværdigheder SET bedømmelse = ? WHERE id = ?";
        
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmtAvg = conn.prepareStatement(sqlAvg)) {
            
            stmtAvg.setLong(1, seværdighedId);
            
            try (ResultSet rs = stmtAvg.executeQuery()) {
                if (rs.next()) {
                    double avgRating = rs.getDouble("avg_rating");
                    
                    try (PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        stmtUpdate.setDouble(1, avgRating);
                        stmtUpdate.setLong(2, seværdighedId);
                        stmtUpdate.executeUpdate();
                        
                        System.out.println("opdateret rating for sev #" + seværdighedId + " til " + avgRating); // debug
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("kunne ikke opdatere gennemsnit: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 