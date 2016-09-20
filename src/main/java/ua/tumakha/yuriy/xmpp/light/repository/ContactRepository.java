package ua.tumakha.yuriy.xmpp.light.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.Contact;

import java.util.Set;

/**
 * @author Yuriy Tumakha.
 */
@Component("contactRepository")
public interface ContactRepository extends CrudRepository<Contact, Long> {

    Set<Contact> findByUser(String user);

    Contact findByUserAndContact(String user, String contact);

}
