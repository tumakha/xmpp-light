package ua.tumakha.yuriy.xmpp.light.service;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ua.tumakha.yuriy.xmpp.light.domain.User;

/**
 * @author Yuriy Tumakha.
 */
@Transactional(readOnly = true)
public interface UserService {

    @Modifying
    @Transactional
    void createUser(User user);

    @Modifying
    @Transactional
    void changePassword(String username, String newPassword);

    User findByUsername(String username);

    boolean verifyCredentials(String username, String passwordCleartext);

}
