package ua.tumakha.yuriy.xmpp.light.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import ua.tumakha.yuriy.xmpp.light.domain.User;

/**
 * @author Yuriy Tumakha.
 */
@Component("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
