package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author mateusz.buchajewicz
 * Initializes data
 */
@ApplicationScoped
public class InitializeData {

    private final UserService userService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final RequestContextController requestContextController;

    @Inject
    public InitializeData(UserService userService, ArticleService articleService, CommentService commentService, RequestContextController requestContextController) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.requestContextController = requestContextController;
    }

    /**
     * Runs after ApplicationScoped.class initializes
     *
     * @param init observed object
     *
     * @throws URISyntaxException thrown if resource URI is not valid
     * @throws IOException thrown if any input/output exception
     */
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) throws URISyntaxException, IOException {
        init();
    }

    /**
     * Creates initial data
     *
     * @throws URISyntaxException thrown if resource URI is not valid
     * @throws IOException thrown if any input/output exception
     */
    private synchronized void init() throws URISyntaxException, IOException {
        requestContextController.activate();
        initUsers();
        initArticles();
        initComments();
        requestContextController.deactivate();
    }

    private void initComments() {
        commentService.createComment(Comment.builder()
                .article(articleService.findAll().get(0))
                .user(userService.findAll().get(0))
                .content("Best book ever!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(5689321L)
                .build());
        commentService.createComment(Comment.builder()
                .article(articleService.findAll().get(0))
                .user(userService.findAll().get(1))
                .content("Damn Nazguls, we couldn't event eat potatoes!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(1234L)
                .build());
        commentService.createComment(Comment.builder()
                .article(articleService.findAll().get(0))
                .user(userService.findAll().get(2))
                .content("@Pippin, look at that!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(-12L)
                .build());

    }

    /**
     * Creates initial articles, taking content from resource/articles
     *
     * @throws URISyntaxException thrown if resource URI is not valid
     * @throws IOException thrown if any input/output exception
     */
    private void initArticles() throws IOException, URISyntaxException {
        Path fellowShipOfTheRing = Paths.get(getClass().getResource("/articles/fellowshipOfTheRing.txt").toURI());
        this.articleService.create(Article.builder()
                .title("The Fellowship of the ring")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 7, 29, 12, 0))
                .content(Files.readString(fellowShipOfTheRing))
                .numberOfLikes(2351457L)
                .build());

        Path twoTowers = Paths.get(getClass().getResource("/articles/twoTowers.txt").toURI());
        this.articleService.create(Article.builder()
                .title("The Two Towers")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 11, 11, 12, 0))
                .content(Files.readString(twoTowers))
                .numberOfLikes(717321L)
                .build());

        Path returnOfTheKing = Paths.get(getClass().getResource("/articles/returnOfTheKing.txt").toURI());
        this.articleService.create(Article.builder()
                .title("The Return of the king")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 10, 20, 12, 0))
                .content(Files.readString(returnOfTheKing))
                .numberOfLikes(678820L)
                .build());
    }

    /**
     * Creates initial users
     */
    private void initUsers() {
        userService.createUser(User.builder()
                .username("frodo")
                .password(Sha256HashingUtility.hash("admin"))
                .birthdate(LocalDate.of(2980, 9, 22))
                .build()
        );
        userService.createUser(User.builder()
                .username("sam")
                .password(Sha256HashingUtility.hash("potatoes"))
                .birthdate(LocalDate.of(2983, 5, 1))
                .build()
        );
        userService.createUser(User.builder()
                .username("merry")
                .password(Sha256HashingUtility.hash("qwerty"))
                .birthdate(LocalDate.of(2982, 12, 2))
                .build()
        );
        userService.createUser(User.builder()
                .username("pippin")
                .password(Sha256HashingUtility.hash("okoń"))
                .birthdate(LocalDate.of(2980, 3, 2))
                .build()
        );
    }
}
