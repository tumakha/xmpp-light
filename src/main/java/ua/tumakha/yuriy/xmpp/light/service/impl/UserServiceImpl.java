package ua.tumakha.yuriy.xmpp.light.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.tumakha.yuriy.xmpp.light.domain.User;
import ua.tumakha.yuriy.xmpp.light.repository.UserRepository;
import ua.tumakha.yuriy.xmpp.light.service.UserService;

/**
 * @author Yuriy Tumakha.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user) {
        user.setPassword(encodePassword(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        User dbUser = findById(user.getId());
        dbUser.setUsername(user.getUsername());
        dbUser.setAdmin(user.isAdmin());
        String newPassword = user.getPassword();
        if (newPassword != null && newPassword.length() > 0) {
            dbUser.setPassword(encodePassword(newPassword));
        }
        userRepository.save(dbUser);
    }

    @Override
    public void changePassword(String username, String newPassword) {
        User user = findByUsername(username);
        if (user != null) {
            user.setPassword(encodePassword(newPassword));
            userRepository.save(user);
        }
    }

    @Override
    public void removeUser(Long userId) {
        userRepository.delete(findById(userId));
    }

    @Override
    public User findById(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found by userId = " + userId);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public boolean verifyCredentials(String username, String passwordCleartext) {
        User user = findByUsername(username);
        return user != null && comparePasswords(passwordCleartext, user.getPassword());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    private String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean comparePasswords(String passwordCleartext, String encodedPassword) {
        return passwordCleartext != null && encodedPassword != null
                && BCrypt.checkpw(passwordCleartext, encodedPassword);
    }

}
