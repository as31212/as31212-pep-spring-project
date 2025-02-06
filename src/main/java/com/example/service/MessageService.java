package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.InvalidMessageTextException;
import com.example.exception.UserNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

 
    public Message postMessage(Message message) throws InvalidMessageTextException, UserNotFoundException{
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            throw new InvalidMessageTextException("Invalid Message Text");
        }

        Optional<Account> user = accountRepository.findById(message.getPostedBy());
        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        return messageRepository.save(message);
    }

    public List<Message> fetchAllMessages(){
       return messageRepository.findAll();
    }

    public Optional<Message> fetchMessageById(int id){
        return messageRepository.findById(id);
    }

    public int deleteById(int id) {
        Optional<Message> message = messageRepository.findById(id);
        if(message.isEmpty()){
            return 0;
        }
        messageRepository.deleteById(id);
        return 1;
    }

    public int updateMessageTextById(int id,String messageText) throws Exception{
        Optional<Message> message = messageRepository.findById(id);
        if(message.isEmpty()){
            throw new Exception("Message not found");
        }

        if(messageText.length() > 255 || messageText.isBlank() ){
            throw new Exception("Invalid message text");
        }
        message.get().setMessageText(messageText);
        messageRepository.save(message.get());
        return 1;
    }

    public List<Message> fetchAllMessagesByUserId(int id){
       return messageRepository.findMessagesByPostedBy(id);
    }
}
