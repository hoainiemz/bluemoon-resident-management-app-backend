package com.example.backend.service;

import com.example.backend.model.Account;
import com.example.backend.repository.AccountRepository;
import com.example.backend.repository.RepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RestController
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RepositoryImpl repositoryImpl;

    @GetMapping("/account/checkaccountexistbyusername")
    @Transactional
    public boolean checkAccountExistByUsername(@RequestParam String username) {
        return accountRepository.existsByUsername(username);
    }


    @GetMapping("/account/checkaccountexistbyemail")
    @Transactional
    public boolean checkAccountExistByEmail(@RequestParam String email) {
        return accountRepository.existsByEmail(email);
    }

    @GetMapping("/account/checkaccountexistbyphone")
    @Transactional
    public boolean checkAccountExistByPhone(String phone) {
        return accountRepository.existsByPhone(phone);
    }

    @PostMapping("/account/createaccount")
    public void createAccount(@RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phone) {
        Account acc = new Account(null, username, email, phone, password, null);
        for (int i = 0; i < 10; i++) {
            try {
                accountRepository.save(acc);
                break;
            }
            catch (Exception e) {
                continue;
            }
        }
    }

    @GetMapping("/account/findaccountbyusernameandpassword")
    public Account findAccountByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        Optional<Account> result = accountRepository.findByUsernameAndPasswordHash(username, password);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    @PutMapping("/account/passwordchangequery")
    public int passwordChangeQuery(@RequestParam String username, @RequestParam String newpassword) {
        return accountRepository.updatePasswordByUsername(username, newpassword);
    }

    @PutMapping("/account/updateaccount")
    public void updateAccount(@RequestBody Account account) {
        accountRepository.updateRowByUserId(account.getUserId(), account.getRole(), account.getEmail(), account.getPhone());
    }

    @GetMapping("/account/findaccountbyuserid")
    public Account findAccountByUserId(@RequestParam int id) {
        return accountRepository.findByUserId(id).get();
    }

    @GetMapping("/account/existbyemail")
    public boolean existsByEmail(@RequestParam String email) {
        return accountRepository.existsByEmail(email);
    }

    @GetMapping("/account/existbyphone")
    public boolean existsByPhone(@RequestParam String phone) {
        return accountRepository.existsByPhone(phone);
    }

    @GetMapping("/account/nativeaccountquery")
    public List<Account> nativeAccountQuery(@RequestParam String query) {
        return repositoryImpl.executeRawSql(query, Account.class);
    }

    @GetMapping("/account/findbyemail")
    public Optional<Account> findByEmail(@RequestParam String email) {
        return accountRepository.findByEmail(email);
    }

    @PostMapping("/account/saveaccount")
    public void save(@RequestBody Account account) {
        accountRepository.save(account);
    }
}
