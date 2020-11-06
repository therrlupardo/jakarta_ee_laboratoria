package pl.edu.pg.eti.kask.blog.user.converter;

import pl.edu.pg.eti.kask.blog.user.model.UserModel;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 * @author mateusz.buchajewicz
 * Converter for class {@link UserModel}
 */
@ManagedBean
@RequestScoped
@FacesConverter(forClass = UserModel.class, managed = true)
public class UserConverter implements Converter<UserModel> {

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) { this.userService = userService; }

    @Override
    public UserModel getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s == null || s.isBlank()) {
            return null;
        }
       return userService.findAll().stream()
                .filter(u -> u.getUsername().equals(s))
                .findFirst()
                .map(UserModel::convertFromEntity)
               .orElse(null);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, UserModel user) {
        return user == null ? "": user.getUsername();
    }
}
