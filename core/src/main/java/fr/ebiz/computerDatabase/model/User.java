package fr.ebiz.computerDatabase.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by ebiz on 31/05/17.
 */
@Entity
@Table(schema = " computer-database-db ", name = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id ;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

public User(){

}

    public User( String login,String passeword) {
        this.login = login;
        this.password=passeword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasseword() {
        return password;
    }


}
