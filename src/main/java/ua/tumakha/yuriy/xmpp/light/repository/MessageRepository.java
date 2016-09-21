package ua.tumakha.yuriy.xmpp.light.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.Message;

/**
 * @author Yuriy Tumakha.
 */
@Component("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long> {

}
