package ua.tumakha.yuriy.xmpp.light.storage;

import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.modules.roster.*;
import org.apache.vysper.xmpp.modules.roster.persistence.AbstractRosterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.Contact;
import ua.tumakha.yuriy.xmpp.light.domain.ContactGroup;
import ua.tumakha.yuriy.xmpp.light.service.ContactService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yuriy Tumakha.
 */
@Component
public class DbContactsRoster extends AbstractRosterManager {

    private static final Logger LOG = LoggerFactory.getLogger(DbContactsRoster.class);

    @Autowired
    private Environment env;

    @Autowired
    private ContactService contactService;

    private String domain;

    @PostConstruct
    public void init() {
        domain = env.getProperty("xmpp.domain");
    }

    @Override
    protected Roster addNewRosterInternal(Entity jid) {
        return null;
    }

    @Override
    protected Roster retrieveRosterInternal(Entity bareJid) {
        return toRoster(contactService.findByUsername(bareJid.getNode()));
    }

    @Override
    public void addContact(Entity jidUser, RosterItem rosterItem) throws RosterException {
        contactService.saveContact(toContact(jidUser, rosterItem));
    }

    @Override
    public void removeContact(Entity jidUser, Entity jidContact) throws RosterException {
        contactService.removeContact(jidUser.getNode(), jidContact.getNode());
    }

    private Contact toContact(Entity jidUser, RosterItem rosterItem) {
        Contact contact = new Contact();
        try {
            contact.setUser(jidUser.getNode());
            contact.setContact(rosterItem.getJid().getNode());
            contact.setFullName(rosterItem.getName());
            contact.setSubscriptionType(rosterItem.getSubscriptionType());
            contact.setAskSubscriptionType(rosterItem.getAskSubscriptionType());
            if (rosterItem.getGroups() != null) {
                Set<ContactGroup> groups = new HashSet<>();
                for (RosterGroup group : rosterItem.getGroups()) {
                    groups.add(new ContactGroup(group.getName()));
                }
                contact.setGroups(groups);
            }
            return contact;
        } catch (Exception e) {
            LOG.error("Convert RosterItem to Contact failed", e);
        }
        return null;
    }

    private Roster toRoster(Set<Contact> contacts) {
        MutableRoster roster = new MutableRoster();
        if (contacts != null) {
            try {
                for (Contact contact : contacts) {
                    List<RosterGroup> groups = new ArrayList<>();
                    if (contact.getGroups() != null) {
                        for (ContactGroup group : contact.getGroups()) {
                            groups.add(new RosterGroup(group.getName()));
                        }
                    }

                    Entity jid = new EntityImpl(contact.getContact(), domain, null);
                    RosterItem item = new RosterItem(jid, contact.getFullName(),
                            contact.getSubscriptionType(), contact.getAskSubscriptionType(), groups);
                    LOG.debug("Item loaded for " + contact.getUser() + ": " + item.toString());
                    roster.addItem(item);
                }
            } catch (Exception e) {
                LOG.error("Convert contacts list to Roster failed", e);
            }
        }
        return roster;
    }

}
