package ru.utlc.financialmanagementservice.dto.invoice;

import ru.utlc.financialmanagementservice.dto.auditinginfo.AuditingInfoDto;
import ru.utlc.financialmanagementservice.dto.client.ClientReadDto;
import ru.utlc.financialmanagementservice.dto.currency.CurrencyReadDto;
import ru.utlc.financialmanagementservice.dto.invoicestatus.InvoiceStatusReadDto;
import ru.utlc.financialmanagementservice.dto.servicetype.ServiceTypeReadDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceEnrichedReadDto(
        Long id,
        ClientReadDto client,
        ServiceTypeReadDto serviceType,
        BigDecimal totalAmount,
        CurrencyReadDto currency,
        LocalDate issueDate,
        LocalDate dueDate,
        String commentary,
        Long shipmentId,
        InvoiceStatusReadDto invoiceStatus,
        AuditingInfoDto auditingInfoDto
) {
}

