package fr.ebiz.computerDatabase.service;

import fr.ebiz.computerDatabase.model.User;
import fr.ebiz.computerDatabase.persistence.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    @Autowired
    private UserDAO userDAO;

    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user = userDAO.findUserByLogin(login);
            if (user != null) {
                ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

                return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasseword(), grantedAuthorities);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
return null;
    }
}