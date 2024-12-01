package com.Banking.banking_app.service.impl;

import com.Banking.banking_app.Dto.AccountDto;
import com.Banking.banking_app.entity.Account;
import com.Banking.banking_app.exception.ResourceNotFoundException;
import com.Banking.banking_app.mapper.AccountMapper;
import com.Banking.banking_app.repository.AccountRepository;
import com.Banking.banking_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {


    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = (Account)this.accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }
    public AccountDto getAccountById(Long id) {
        Account account = (Account)this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with given id : " + id));
        return AccountMapper.mapToAccountDto(account);
    }


    public AccountDto deposit(Long id, double amount) {
        Account account = (Account)this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with given id : " + id));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account newbal = (Account)this.accountRepository.save(account);
        return AccountMapper.mapToAccountDto(newbal);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = (Account)this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with given id : " + id));
        double currbal = account.getBalance();
        if (amount > currbal) {
            throw new ResourceNotFoundException("Insufficient balance");
        } else {
            double newbal = account.getBalance() - amount;
            account.setBalance(newbal);
            Account account1 = (Account)this.accountRepository.save(account);
            return AccountMapper.mapToAccountDto(account1);
        }
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = this.accountRepository.findAll();
        return (List)accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account var10000 = (Account)this.accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with given id : " + id));
        this.accountRepository.deleteById(id);
    }
}
