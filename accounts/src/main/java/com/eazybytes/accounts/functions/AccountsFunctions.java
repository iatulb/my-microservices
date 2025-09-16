package com.eazybytes.accounts.functions;

import com.eazybytes.accounts.service.IAccountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class AccountsFunctions {
    @Bean
    public Consumer<Long> updateCommunication (IAccountsService iAccountsService){
        return accountNumber -> {
          log.info("updating communication status for the account number {} ", accountNumber.toString());
          iAccountsService.updateCommuncationStatus(accountNumber);
        };
    }
}
