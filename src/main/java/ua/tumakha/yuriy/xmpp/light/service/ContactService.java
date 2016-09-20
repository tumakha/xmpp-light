package ua.tumakha.yuriy.xmpp.light.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ua.tumakha.yuriy.xmpp.light.domain.Contact;

import java.util.Set;

/**
 * @author Yuriy Tumakha.
 */
@Transactional(readOnly = true)
public interface ContactService {

    Set<Contact> findByUsername(String username);

    @Modifying
    @Transactional
    void saveContact(Contact contact);

    @Modifying
    @Transactional
    void removeContact(String username, String contact);

}
