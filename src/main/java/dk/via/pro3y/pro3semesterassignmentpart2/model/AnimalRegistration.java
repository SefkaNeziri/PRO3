package dk.via.pro3y.pro3semesterassignmentpart2.model;

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
