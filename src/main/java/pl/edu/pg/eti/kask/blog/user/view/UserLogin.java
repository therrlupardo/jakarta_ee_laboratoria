package pl.edu.pg.eti.kask.blog.user.view;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.blog.user.authentication.AuthenticationService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.login.CredentialException;
import java.io.Serializable;

/**
 * @author mateusz.buchajewicz
 * View bean for login form
 */
@Named
@SessionScoped
public class UserLogin implements Serializable {

    private final AuthenticationService authenticationService;
    /**
     * Value of form's login field
     */
    @Getter
    @Setter
    private String login;
    /**
     * Value of form's password field
     */
    @Getter
    @Setter
    private String password;

    @Inject
    public UserLogin(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Checks user's authenticity and if passing logs into application
     *
     * @return navigation path to same page
     */
    public String loginAction() {
        authenticationService.authenticate(login, password);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
