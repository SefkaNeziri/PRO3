package dk.via.pro3y.pro3semesterassignmentpart2.repository;

import dk.via.pro3y.pro3semesterassignmentpart2.model.ProductData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductData> findByAnimalRegistrationNumber(
            int registrationNumber) {

        String sql = """
                SELECT p.product_id
                FROM product p
                JOIN product_animal pa
                    ON p.product_id = pa.product_id
                WHERE pa.registration_number = ?
                ORDER BY p.product_id
                """;

        List<Integer> productIds = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        rs.getInt("product_id"),
                registrationNumber
        );

        List<ProductData> products = new ArrayList<>();

        for (Integer productId : productIds) {
            products.add(findById(productId));
        }

        return products;
    }

    public ProductData findById(int productId) {

        String sql = """
                SELECT registration_number
                FROM product_animal
                WHERE product_id = ?
                ORDER BY registration_number
                """;

        List<Integer> registrationNumbers = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        rs.getInt("registration_number"),
                productId
        );

        return new ProductData(
                productId,
                registrationNumbers.stream()
                        .mapToInt(Integer::intValue)
                        .toArray()
        );
    }
}