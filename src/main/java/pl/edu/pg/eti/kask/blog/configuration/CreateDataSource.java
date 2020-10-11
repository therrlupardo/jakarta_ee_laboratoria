package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.datastore.DataStore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author mateusz.buchajewicz
 * Servlet listener, which creates data source
 */
@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Path path = Paths.get(System.getProperty("user.home"), "avatars");
        sce.getServletContext().setAttribute("datasource", new DataStore(path));
    }
}
