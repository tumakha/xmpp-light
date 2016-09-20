package ua.tumakha.yuriy.xmpp.light.storage;

import org.apache.vysper.xmpp.addressing.Entity;
import org.apache.vysper.xmpp.addressing.EntityFormatException;
import org.apache.vysper.xmpp.addressing.EntityImpl;
import org.apache.vysper.xmpp.authorization.AccountCreationException;
import org.apache.vysper.xmpp.authorization.AccountManagement;
import org.apache.vysper.xmpp.authorization.UserAuthorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.User;
import ua.tumakha.yuriy.xmpp.light.service.UserService;

/**
 * @author Yuriy Tumakha.
 */
@Component
public class DbUserManagement implements UserAuthorization, AccountManagement {

    private static final Logger LOG = LoggerFactory.getLogger(DbUserManagement.class);

    @Autowired
    private UserService userService;

    @Override
    public void addUser(Entity username, String password) throws AccountCreationException {
        User user = new User();
        user.setUsername(username.getNode());
        user.setPassword(password);
        userService.createUser(user);
    }

    @Override
    public void changePassword(Entity username, String password) throws AccountCreationException {
        userService.changePassword(username.getNode(), password);
    }

    @Override
    public boolean verifyAccountExists(Entity jid) {
        return userService.findByUsername(jid.getNode()) != null;
    }

    @Override
    public boolean verifyCredentials(Entity jid, String passwordCleartext, Object credentials) {
        return userService.verifyCredentials(jid.getNode(), passwordCleartext);
    }

    @Override
    public boolean verifyCredentials(String username, String passwordCleartext, Object credentials) {
        try {
            Entity jid = EntityImpl.parse(username);
            return verifyCredentials(jid, passwordCleartext, credentials);
        } catch (EntityFormatException e) {
            LOG.error("Parse JID failed", e);
        }
        return false;
    }

}
