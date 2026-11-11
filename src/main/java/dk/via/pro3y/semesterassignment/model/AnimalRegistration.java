package dk.via.pro3y.semesterassignment.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Complete registration information for an animal arriving at the slaughterhouse. */
public record AnimalRegistration(
        int registrationNumber,
        LocalDate date,
        BigDecimal weight,
        String origin
) {
}