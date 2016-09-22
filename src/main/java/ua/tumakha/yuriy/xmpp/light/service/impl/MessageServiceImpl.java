package ua.tumakha.yuriy.xmpp.light.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.tumakha.yuriy.xmpp.light.domain.Message;
import ua.tumakha.yuriy.xmpp.light.repository.MessageRepository;
import ua.tumakha.yuriy.xmpp.light.service.MessageService;

/**
 * @author Yuriy Tumakha.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

}
