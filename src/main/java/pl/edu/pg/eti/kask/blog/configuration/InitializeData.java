package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InitializeData implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserService userService = (UserService) sce.getServletContext().getAttribute("userService");
        init(userService);
    }

    private synchronized void init(UserService userService) {
        userService.createUser(User.builder()
                .id(0L)
                .username("Frodo Baggins")
                .build()
        );
        userService.createUser(User.builder()
                .id(1L)
                .username("Samwise Gamgee")
                .build()
        );
        userService.createUser(User.builder()
                .id(2L)
                .username("Meriadoc Brandybuck")
                .build()
        );
        userService.createUser(User.builder()
                .id(3L)
                .username("Peregrin Took")
                .build()
        );
    }
}
