package ru.utlc.financialmanagementservice.dto.currency;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CurrencyCreateUpdateDto(
        @NotNull(message = "validation.currency.code.required")
        @Pattern(regexp = "^[A-Z]{3,4}$", message = "validation.currency.code.pattern")
        String code,

        @Size(min = 2, max = 50, message = "validation.currency.name.size")
        String name,

        Boolean enabled
) {
}
