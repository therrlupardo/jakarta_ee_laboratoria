package pl.edu.pg.eti.kask.blog.user.context;

import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author mateusz.buchajewicz
 * Information about logged in user, stored is session scope
 */
@Named
@SessionScoped
public class UserContext implements Serializable {

    /**
     * Logged user username
     */
    @Getter
    @Setter
    private String principal;

    /**
     * Checks if user is logged and authorized to perform actions
     * @return true if user is logged in and autorized
     */
    public boolean isAuthorized() {
        return principal != null;
    }
}
