package ua.tumakha.yuriy.xmpp.light.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tumakha.yuriy.xmpp.light.domain.Contact;
import ua.tumakha.yuriy.xmpp.light.domain.ContactGroup;
import ua.tumakha.yuriy.xmpp.light.repository.ContactRepository;
import ua.tumakha.yuriy.xmpp.light.service.ContactService;

import java.util.Set;

/**
 * @author Yuriy Tumakha.
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Set<Contact> findByUsername(String username) {
        return contactRepository.findByUser(username);
    }

    @Override
    public void saveContact(Contact contact) {
        Contact dbContact = contactRepository.findByUserAndContact(contact.getUser(), contact.getContact());
        if (dbContact == null) {
            dbContact = contact;
        } else {
            dbContact.setFullName(contact.getFullName());
            dbContact.setSubscriptionType(contact.getSubscriptionType());
            dbContact.setAskSubscriptionType(contact.getAskSubscriptionType());
            dbContact.getGroups().clear();
            dbContact.setGroups(contact.getGroups());
        }
        if (dbContact.getGroups() != null) {
            for (ContactGroup group : dbContact.getGroups()) {
                group.setContact(dbContact);
            }
        }
        contactRepository.save(dbContact);
    }

    @Override
    public void removeContact(String username, String contact) {
        Contact dbContact = contactRepository.findByUserAndContact(username, contact);
        if (dbContact != null) {
            contactRepository.delete(dbContact);
        }
    }

}
