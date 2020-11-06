package pl.edu.pg.eti.kask.blog.user.view;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.blog.user.authentication.AuthenticationService;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author mateusz.buchajewicz
 * View bean for login form
 */
@Named
@SessionScoped
public class UserLogin implements Serializable {

    private AuthenticationService authenticationService;

    @Inject
    public void setAuthenticationService(AuthenticationService authenticationService) { this.authenticationService = authenticationService; }
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

    /**
     * Checks user's authenticity and if passing logs into application
     *
     * @throws IOException thrown if any input/output exception
     * @return navigation path to same page
     */
    public String loginAction() throws IOException {
        boolean authenticated = authenticationService.authenticate(login, password);
        if (!authenticated) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_UNAUTHORIZED, "Wrong login or password");
        }
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
