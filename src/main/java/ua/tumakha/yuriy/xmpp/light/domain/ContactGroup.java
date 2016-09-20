package ua.tumakha.yuriy.xmpp.light.domain;

import javax.persistence.*;

/**
 * @author Yuriy Tumakha.
 */
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "contactId"})
})
public class ContactGroup {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "contactId", nullable = false)
    private Contact contact;

    public ContactGroup() {
    }

    public ContactGroup(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

}
