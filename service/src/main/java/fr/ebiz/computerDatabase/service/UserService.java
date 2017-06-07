package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.model.User;
import fr.ebiz.computerDatabase.persistence.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by ebiz on 31/05/17.
 */
@Service
public class UserService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);
    @Autowired
    private UserDAO userDAO;

    /**
     * @param login of user
     * @return a user details
     * @throws UsernameNotFoundException if userName not found
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user = userDAO.findUserByLogin(login);
            if (user != null) {
                ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasseword(), grantedAuthorities);
            }
        } catch (UsernameNotFoundException e) {
            LOGGER.error("[Error Service] in function loadUserByUsername");
        }
        return null;
    }
}