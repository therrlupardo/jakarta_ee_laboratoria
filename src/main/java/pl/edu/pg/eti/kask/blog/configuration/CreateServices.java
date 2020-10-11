package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.avatar.repository.AvatarRepository;
import pl.edu.pg.eti.kask.blog.avatar.service.AvatarService;
import pl.edu.pg.eti.kask.blog.datastore.DataStore;
import pl.edu.pg.eti.kask.blog.user.repository.UserRepository;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author mateusz.buchajewicz
 * Servlet listener, which creates all needed services
 * Should be initialized after creation of datasource
 * @see CreateDataSource
 */
@WebListener
public class CreateServices implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataStore dataStore = (DataStore) sce.getServletContext().getAttribute("datasource");
        sce.getServletContext().setAttribute("userService", new UserService(new UserRepository(dataStore)));
        sce.getServletContext().setAttribute("avatarService", new AvatarService(new AvatarRepository(dataStore)));
    }
}
