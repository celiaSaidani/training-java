package fr.ebiz.computerDatabase.persistence;

import fr.ebiz.computerDatabase.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ebiz on 31/05/17.
 */
public interface UserDAO extends JpaRepository<User, Long> {
    /**
     *
     * @param login to access in application
     * @return a user from data base
     */
    User findUserByLogin(String login);
}
