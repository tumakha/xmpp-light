package ua.tumakha.yuriy.xmpp.light.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ua.tumakha.yuriy.xmpp.light.domain.Message;

/**
 * @author Yuriy Tumakha.
 */
@Transactional(readOnly = true)
public interface MessageService {

    @Modifying
    @Transactional
    void saveMessage(Message message);

    Page<Message> findAll(Pageable pageable);

}
