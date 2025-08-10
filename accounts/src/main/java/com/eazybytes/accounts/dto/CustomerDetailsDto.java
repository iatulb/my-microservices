package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Schema(
        name = "Customer details",
        description = "Schema to hold Customer, account and Account information"
)
public class CustomerDetailsDto extends CustomerDto {
    @Schema(
            description = "loans details of the Customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "cards details of the Customer"
    )
    private CardsDto cardsDto;

}
