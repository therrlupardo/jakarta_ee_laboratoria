package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;

/**
 * @author mateusz.buchajewicz
 * Web listener which creates all application's data
 * Should be initialized after UserService,
 * @see CreateServices
 */
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
                .birthdate(LocalDate.of(2980, 9, 22))
                .build()
        );
        userService.createUser(User.builder()
                .id(1L)
                .username("Samwise Gamgee")
                .birthdate(LocalDate.of(2983, 5, 1))
                .build()
        );
        userService.createUser(User.builder()
                .id(2L)
                .username("Meriadoc Brandybuck")
                .birthdate(LocalDate.of(2982, 12, 2))
                .build()
        );
        userService.createUser(User.builder()
                .id(3L)
                .username("Peregrin Took")
                .birthdate(LocalDate.of(2980, 3, 2))
                .build()
        );
    }
}
