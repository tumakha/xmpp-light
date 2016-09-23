package ua.tumakha.yuriy.xmpp.light.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.tumakha.yuriy.xmpp.light.domain.User;
import ua.tumakha.yuriy.xmpp.light.service.UserService;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

/**
 * @author Yuriy Tumakha.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null || !user.isAdmin()) {
            throw new UsernameNotFoundException(String.format("User '%s' with role 'ADMIN' was not found", username));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), createAuthorityList("ADMIN"));
    }

}
