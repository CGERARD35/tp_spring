package projetPOEIspring.poeidata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.server.ResponseStatusException;
import projetPOEIspring.poeidata.exceptions.UnknownResourceException;
import projetPOEIspring.poeidata.models.User;
import projetPOEIspring.poeidata.services.UserService;

public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User existingUser = this.userService.getByUsername(username);
            return new MyUserDetails(existingUser);
        } catch (UnknownResourceException ure) {
            throw new UsernameNotFoundException("User with username" + username + "not found");
        }
    }
}
