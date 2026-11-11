package dk.via.pro3y.semesterassignment.rest;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateAnimalRequest(
        @Positive(message = "Registration number must be positive")
        int registrationNumber,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Weight is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than zero")
        BigDecimal weight,

        @NotBlank(message = "Origin is required")
        String origin
) {
}