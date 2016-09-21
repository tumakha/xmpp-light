package ua.tumakha.yuriy.xmpp.light.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.User;

/**
 * @author Yuriy Tumakha.
 */
@Component("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
