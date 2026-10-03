package dk.via.pro3y.pro3semesterassignmentpart2.repository;

import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnimalRepository {

    private final JdbcTemplate jdbcTemplate;

    public AnimalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AnimalData> findByProductId(int productId) {

        String sql = """
                SELECT a.registration_number
                FROM animal a
                JOIN product_animal pa
                    ON a.registration_number = pa.registration_number
                WHERE pa.product_id = ?
                ORDER BY a.registration_number
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new AnimalData(
                                rs.getInt("registration_number")
                        ),
                productId
        );
    }
}