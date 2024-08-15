package ru.utlc.financialmanagementservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.utlc.financialmanagementservice.dto.currency.CurrencyCreateUpdateDto;
import ru.utlc.financialmanagementservice.dto.currency.CurrencyReadDto;
import ru.utlc.financialmanagementservice.response.Response;
import ru.utlc.financialmanagementservice.service.CurrencyService;
import ru.utlc.financialmanagementservice.util.ValidationErrorUtil;

import java.net.URI;
import java.util.List;

import static ru.utlc.financialmanagementservice.constants.ApiPaths.CURRENCIES;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(CURRENCIES)
public class CurrencyRestController {

    private final CurrencyService currencyService;

    @GetMapping
    public Mono<ResponseEntity<List<CurrencyReadDto>>> findAll() {
        return currencyService.findAll()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CurrencyReadDto>> findById(@PathVariable("id") final Integer id) {
        return currencyService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json")
    public Mono<ResponseEntity<Response>> create(@RequestBody @Valid CurrencyCreateUpdateDto dto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ValidationErrorUtil.handleValidationErrors(bindingResult);
        }

        return currencyService.create(dto)
                .map(currencyReadDto -> {
                    URI location = URI.create("/currencies/" + currencyReadDto.id());
                    return ResponseEntity.created(location).body(new Response(currencyReadDto));
                });
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Mono<ResponseEntity<Response>> update(@PathVariable("id") final Integer id,
                                                 @RequestBody @Valid CurrencyCreateUpdateDto dto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ValidationErrorUtil.handleValidationErrors(bindingResult);
        }

        return currencyService.update(id, dto)
                .map(updatedDto -> new ResponseEntity<>(new Response(updatedDto), HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") final Integer id) {
        return currencyService.delete(id)
                .flatMap(deleted -> Boolean.TRUE.equals(deleted)
                        ? Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT))
                        : Mono.just(new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
}