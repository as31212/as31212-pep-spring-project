package com.example.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/messages")
    public @ResponseBody ResponseEntity<?> fetchAll(){
        return ResponseEntity.status(200).body(messageService.fetchAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<?> fetchById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.fetchMessageById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<?> deleteById(@PathVariable int messageId){
        int delete = messageService.deleteById(messageId);

        if(delete == 1){
            return ResponseEntity.status(200).body(delete);
        }

        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{messageId}")
    public @ResponseBody ResponseEntity<?> updateById(@PathVariable int messageId, @RequestBody Map<String, String> requestBody) {
    try {
        String messageText = requestBody.get("messageText");
        return ResponseEntity.status(200).body(messageService.updateMessageTextById(messageId, messageText));
    } catch (Exception e) {
        return ResponseEntity.status(400).body(e.getMessage());
    } 
}

    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<?> fetchUserMessages(@PathVariable int accountId){
        return ResponseEntity.status(200).body(messageService.fetchAllMessagesByUserId(accountId));
    }

    @PostMapping("/messages")
    public @ResponseBody ResponseEntity<?> createMessage(@RequestBody Message message){
        try {
            return ResponseEntity.status(200).body(messageService.postMessage(message));
            
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}


