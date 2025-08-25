package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CustomerDetailsDto;
import com.eazybytes.accounts.dto.ErrorResponseDto;
import com.eazybytes.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/", produces = "application/json")
@Tag(
        name = "REST APIs for customer in EazyBank",
        description = "REST APIs in EazyBank to fetch customer details"
)
@Validated
@Slf4j
public class CustomerController {

    @Autowired
    private ICustomerService iCustomerService;

    @Operation(
            summary = "Fetch customer Details REST API",
            description = "REST API to fetch customer details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping(path = "/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> getCustomerDetails(
            @RequestHeader("iatulb-correlation-id") String correlationId,
            @RequestParam
                                                                 @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                                 String mobileNumber) {

        log.debug("fetchCustomerDetails start");
        CustomerDetailsDto customerDetailsDto = iCustomerService.getCustomerDetailsByMobileNumber(mobileNumber, correlationId);
        log.debug("fetchCustomerDetails end");
        return ResponseEntity.ok(customerDetailsDto);
    }
}
