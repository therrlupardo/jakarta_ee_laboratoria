package pl.edu.pg.eti.kask.blog.user.view;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author matesz.buchajewicz
 * View bean for logout form
 */
@Named
@RequestScoped
public class UserLogout implements Serializable {

    /**
     * Invalidates current's user session
     * @return navigation path to same page
     */
    public String logoutAction() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }
}
