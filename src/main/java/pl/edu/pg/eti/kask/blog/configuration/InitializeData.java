package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.time.LocalDate;

/**
 * @author mateusz.buchajewicz
 * Initializes data
 */
@ApplicationScoped
public class InitializeData  {

    private final UserService userService;

    @Inject
    public InitializeData(UserService userService) {
        this.userService = userService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init() {
        userService.createUser(User.builder()
                .id(0L)
                .username("frodo")
                .password("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918") // admin
                .birthdate(LocalDate.of(2980, 9, 22))
                .build()
        );
        userService.createUser(User.builder()
                .id(1L)
                .username("sam")
                .password("292bdd381b06dfb9fee4a0be10048bb6e8331963b3a4ec490ce0dab333468040") // potatoes
                .birthdate(LocalDate.of(2983, 5, 1))
                .build()
        );
        userService.createUser(User.builder()
                .id(2L)
                .username("merry")
                .password("65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5") // qwerty
                .birthdate(LocalDate.of(2982, 12, 2))
                .build()
        );
        userService.createUser(User.builder()
                .id(3L)
                .username("pippin")
                .password("0ca18fa26bd10ec5f20286a5364937b969c73a934b96610005c67cedb9d13ae3") // okoń
                .birthdate(LocalDate.of(2980, 3, 2))
                .build()
        );
    }
}
