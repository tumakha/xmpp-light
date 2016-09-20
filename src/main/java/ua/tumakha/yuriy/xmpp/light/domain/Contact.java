package ua.tumakha.yuriy.xmpp.light.domain;

import org.apache.vysper.xmpp.modules.roster.AskSubscriptionType;
import org.apache.vysper.xmpp.modules.roster.SubscriptionType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yuriy Tumakha.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user", "contact"})
})
public class Contact {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Roster owner username.
     */
    @Column(nullable = false)
    private String user;

    /**
     * Contact item username.
     */
    @Column(nullable = false)
    private String contact;

    /**
     * a user-chosen, descriptive, often short name ('nick'), eg. "Frank Zappa", or "Frank"
     */
    private String fullName;

    /**
     * all the groups the item is displayed under. this list can be empty.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
    private Set<ContactGroup> groups = new HashSet<>();

    /**
     * type of subscription either FROM, TO or both. depending on the value of askSubscriptionType, FROM or TO
     * subscriptions might be still pending and awaiting approval
     */
    @Enumerated(EnumType.STRING)
    private SubscriptionType subscriptionType;

    /**
     * records pending subscriptions, awaiting approval
     */
    @Enumerated(EnumType.STRING)
    private AskSubscriptionType askSubscriptionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFullName() {
        return fullName == null ? contact : fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<ContactGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<ContactGroup> groups) {
        this.groups = groups;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public AskSubscriptionType getAskSubscriptionType() {
        return askSubscriptionType;
    }

    public void setAskSubscriptionType(AskSubscriptionType askSubscriptionType) {
        this.askSubscriptionType = askSubscriptionType;
    }

}
