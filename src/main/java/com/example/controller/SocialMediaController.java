package com.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidLoginPasswordException;
import com.example.exception.InvalidRegistrationException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService,MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody Account account){

        try {
            return ResponseEntity.status(200).body(accountService.registerAccount(account));
            
            
        }catch(InvalidRegistrationException e){
            return ResponseEntity.status(400).body(e.getMessage());
            
        }
        catch(DuplicateUsernameException e){
            return ResponseEntity.status(409).body(e.getMessage());
        } 
        catch (Exception e) {
            return ResponseEntity.status(400).body("Unexpected error occurred");
        }
    }

    
    @PostMapping("login")
    public @ResponseBody ResponseEntity<?> login(@RequestBody Account account){
       try {
            return ResponseEntity.status(200).body(accountService.login(account));
       }
       catch(InvalidLoginException e){
            return ResponseEntity.status(401).body(e.getMessage());
       }
       catch (InvalidLoginPasswordException e) {
            return ResponseEntity.status(401).body(e.getMessage());
       }
       catch(Exception e){
            return ResponseEntity.status(401).body("Unexpected Error occurred");
       }
    }

}


