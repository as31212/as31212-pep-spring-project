package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidLoginPasswordException;
import com.example.exception.InvalidRegistrationException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    private AccountRepository accountRepository;


    // not necessary but still felt the need to provide the annotation
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    public Account registerAccount(Account account) throws DuplicateUsernameException,InvalidRegistrationException{
        // check to determine if username is blank or if password is less than 4 characters
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            throw new InvalidRegistrationException("Username cannot be blank, and password must be at least 4 characters long.");
        }

        // check to determine if if username exists
        if(accountRepository.findAccountByUsername(account.getUsername()) != null){
         throw new DuplicateUsernameException("Username already exists");
      }

      return accountRepository.save(account);
    }

    
    public Account login(Account account)throws InvalidLoginException,InvalidLoginPasswordException{

        Account user = accountRepository.findAccountByUsername(account.getUsername());
        // determine if username is matching
        if(user == null){
            throw new InvalidLoginException("Account does not exist");
        }
        //  determine if password is matching
        if(!user.getPassword().equals(account.getPassword()) ){
            throw new InvalidLoginPasswordException("Password is incorrect");
        }
        return user;
    }

}
