package com.Banking.banking_app.controller;

import com.Banking.banking_app.Dto.AccountDto;
import com.Banking.banking_app.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // add account Rest api
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity(this.accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<AccountDto> getAccountbyId(@PathVariable Long id) {
        AccountDto accountDto = this.accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }
    // Deposit REST API
    @PutMapping({"deposit/{id}"})
    public ResponseEntity<AccountDto> deposit(@PathVariable Long id, @RequestBody Map<String, Double> req) {
        Double amount = (Double)req.get("amount");
        AccountDto newdto = this.accountService.deposit(id, amount);
        return ResponseEntity.ok(newdto);
    }
    // Witdraw money rest Api
    @PutMapping({"withdraw/{id}"})
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id, @RequestBody Map<String, Double> req) {
        Double amount = (Double)req.get("amount");
        AccountDto newdto = this.accountService.withdraw(id, amount);
        return ResponseEntity.ok(newdto);
    }

    // get all account rest api
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = this.accountService.getAllAccounts();
        return accounts.isEmpty() ? new ResponseEntity(HttpStatus.NO_CONTENT) : new ResponseEntity(accounts, HttpStatus.OK);
    }
    // get account rest api
    @DeleteMapping({"/{id}"})
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        this.accountService.deleteAccount(id);
        return new ResponseEntity("Account deleted successfully", HttpStatus.OK);
    }
}
