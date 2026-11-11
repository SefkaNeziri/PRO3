package dk.via.pro3y.semesterassignment.repository;

import dk.via.pro3y.semesterassignment.model.AnimalRegistration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class AnimalRegistrationRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<AnimalRegistration> rowMapper = (rs, rowNum) ->
            new AnimalRegistration(
                    rs.getInt("registration_number"),
                    rs.getDate("arrival_date") == null ? null : rs.getDate("arrival_date").toLocalDate(),
                    rs.getBigDecimal("weight"),
                    rs.getString("origin")
            );

    public AnimalRegistrationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AnimalRegistration save(AnimalRegistration animal) {
        String sql = """
                INSERT INTO animal (registration_number, arrival_date, weight, origin)
                VALUES (?, ?, ?, ?)
                RETURNING registration_number, arrival_date, weight, origin
                """;

        return jdbcTemplate.queryForObject(
                sql, rowMapper,
                animal.registrationNumber(),
                Date.valueOf(animal.date()),
                animal.weight(),
                animal.origin().trim()
        );
    }

    public Optional<AnimalRegistration> findByRegistrationNumber(int registrationNumber) {
        String sql = """
                SELECT registration_number, arrival_date, weight, origin
                FROM animal
                WHERE registration_number = ?
                """;
        return jdbcTemplate.query(sql, rowMapper, registrationNumber).stream().findFirst();
    }

    public List<AnimalRegistration> findByDate(LocalDate date) {
        String sql = """
                SELECT registration_number, arrival_date, weight, origin
                FROM animal
                WHERE arrival_date = ?
                ORDER BY registration_number
                """;
        return jdbcTemplate.query(sql, rowMapper, Date.valueOf(date));
    }

    public List<AnimalRegistration> findByOrigin(String origin) {
        String sql = """
                SELECT registration_number, arrival_date, weight, origin
                FROM animal
                WHERE LOWER(origin) = LOWER(?)
                ORDER BY arrival_date, registration_number
                """;
        return jdbcTemplate.query(sql, rowMapper, origin.trim());
    }
}