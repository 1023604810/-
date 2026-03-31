package cn.fvti.springboot.service;

import cn.fvti.springboot.entity.Message;
import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();
    Message getMessageById(Long id);
    Message saveMessage(Message message);
    Message updateMessage(Message message);
    void deleteMessage(Long id);
}
