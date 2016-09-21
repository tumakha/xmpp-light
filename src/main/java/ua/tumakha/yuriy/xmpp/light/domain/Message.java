package ua.tumakha.yuriy.xmpp.light.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Yuriy Tumakha.
 */
@Entity(name = "message")
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String fromJID;

    @Column(nullable = false)
    private String toJID;

    @Column(nullable = false)
    private Long time;

    @Column(nullable = false, length = 9000)
    private String body;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromJID() {
        return fromJID;
    }

    public void setFromJID(String fromJID) {
        this.fromJID = fromJID;
    }

    public String getToJID() {
        return toJID;
    }

    public void setToJID(String toJID) {
        this.toJID = toJID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
