package ua.tumakha.yuriy.xmpp.light.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    void updateUser(User user);

    @Modifying
    @Transactional
    void changePassword(String username, String newPassword);

    @Modifying
    @Transactional
    void removeUser(Long userId);

    User findById(Long userId);

    User findByUsername(String username);

    boolean verifyCredentials(String username, String passwordCleartext);

    Page<User> findAll(Pageable pageable);

}
