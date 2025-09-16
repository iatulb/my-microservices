package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.dto.AccountsMsgDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.mapper.ObjectMapper;
import com.eazybytes.accounts.repository.IAccountsRepository;
import com.eazybytes.accounts.repository.ICustomerRepository;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    private StreamBridge streamBridge;

    @Autowired
    private IAccountsRepository iAccountsRepository;
    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Override
    public void createAccount(CustomerDto customerDTO) {
        Customer customer = ObjectMapper.mapUsingJson(Customer.class, customerDTO);
        Optional<Customer> byMobileNumber = iCustomerRepository.findByMobileNumber(customerDTO.getMobileNumber());
        if (byMobileNumber.isPresent()){
            throw new CustomerAlreadyExistException("Customer Already exist" + customerDTO.getMobileNumber());
        }
        Customer savedCustomer = iCustomerRepository.save(customer);

        Accounts saveAccounts = iAccountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(saveAccounts, savedCustomer);

    }

    private void sendCommunication(Accounts accounts, Customer customer){
        AccountsMsgDto accountsMsgDto = new AccountsMsgDto(accounts.getAccountNumber(), customer.getName()
                , customer.getEmail(), customer.getMobileNumber());
        log.info("Sending communication message to the queue {}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        log.info("Is the communication request successfully triggered? {}", result);
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = iCustomerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = iAccountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = ObjectMapper.mapUsingJson(CustomerDto.class, customer);
        AccountsDto accountsDto = ObjectMapper.mapUsingJson(AccountsDto.class, accounts);
        customerDto.setAccountsDto(accountsDto);
        return customerDto;

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){

            Accounts accounts = iAccountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = iAccountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = iCustomerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            iCustomerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = iCustomerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        iAccountsRepository.deleteByCustomerId(customer.getCustomerId());
        iCustomerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    @Override
    public boolean updateCommuncationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if (accountNumber != null) {
            Accounts accounts = iAccountsRepository.findById(accountNumber).orElseThrow(() -> new ResourceNotFoundException("Accounts", "AccountNumber", accountNumber.toString()) );
            accounts.setCommunicationSw(true);
            iAccountsRepository.save(accounts);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("Anonymous");
        return newAccount;
    }
}
