package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.datastore.DataStore;
import pl.edu.pg.eti.kask.blog.user.repository.UserRepository;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CreateServices implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DataStore dataStore = (DataStore) sce.getServletContext().getAttribute("datasource");
        sce.getServletContext().setAttribute("userService", new UserService(new UserRepository(dataStore)));
    }
}
