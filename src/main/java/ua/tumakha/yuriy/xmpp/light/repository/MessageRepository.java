package ua.tumakha.yuriy.xmpp.light.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.tumakha.yuriy.xmpp.light.domain.Message;

/**
 * @author Yuriy Tumakha.
 */
@Component("messageRepository")
@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {

}
