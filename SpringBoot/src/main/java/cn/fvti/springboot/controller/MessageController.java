package cn.fvti.springboot.controller;

import cn.fvti.springboot.entity.Message;
import cn.fvti.springboot.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Message> saveMessage(@RequestBody Message message) {
        // 设置默认值
        if (message.getTime() == null) {
            message.setTime(System.currentTimeMillis());
        }
        if (message.getSupport() == null) {
            message.setSupport(0);
        }
        if (message.getHasSup() == null) {
            message.setHasSup(false);
        }
        if (message.getUnnamed() == null) {
            message.setUnnamed(false);
        }

        Message savedMessage = messageService.saveMessage(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message message) {
        Message existingMessage = messageService.getMessageById(id);
        if (existingMessage != null) {
            message.setId(id);
            Message updatedMessage = messageService.updateMessage(message);
            return new ResponseEntity<>(updatedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        Message existingMessage = messageService.getMessageById(id);
        if (existingMessage != null) {
            messageService.deleteMessage(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
