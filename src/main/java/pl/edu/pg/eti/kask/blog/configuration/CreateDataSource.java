package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.datastore.DataStore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("datasource", new DataStore());
    }
}
