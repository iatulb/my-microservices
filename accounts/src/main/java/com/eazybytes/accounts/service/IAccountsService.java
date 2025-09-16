package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

public interface IAccountsService {
    public void createAccount(CustomerDto customerDTO);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(@Valid CustomerDto customerDto);

    boolean deleteAccount(@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber);

    boolean updateCommuncationStatus( Long accountNumber);
}
