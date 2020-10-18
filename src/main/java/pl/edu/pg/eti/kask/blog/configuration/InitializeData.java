package pl.edu.pg.eti.kask.blog.configuration;

import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.article.service.ArticleService;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.comment.service.CommentService;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
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

    @Inject
    public InitializeData(UserService userService, ArticleService articleService, CommentService commentService) {
        this.userService = userService;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    /**
     * Runs after ApplicationScoped.class initializes
     *
     * @param init
     * @throws URISyntaxException
     * @throws IOException
     */
    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) throws URISyntaxException, IOException {
        init();
    }

    /**
     * Creates initial data
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    private synchronized void init() throws URISyntaxException, IOException {
        initUsers();
        initArticles();
        initComments();
    }

    private void initComments() {
        commentService.createComment(Comment.builder()
                .articleId(1L)
                .userId(1L)
                .content("Best book ever!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(5689321L)
                .build());
        commentService.createComment(Comment.builder()
                .articleId(1L)
                .userId(2L)
                .content("Damn Nazguls, we couldn't event eat potatoes!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(1234L)
                .build());
        commentService.createComment(Comment.builder()
                .articleId(1L)
                .userId(3L)
                .content("@Pippin, look at that!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(-12L)
                .build());

    }

    /**
     * Creates initial articles, taking content from resource/articles
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    private void initArticles() throws URISyntaxException, IOException {
        Path fellowShipOfTheRing = Paths.get(getClass().getResource("/articles/fellowshipOfTheRing.txt").toURI());
        this.articleService.createArticle(Article.builder()
                .title("The Fellowship of the ring")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 7, 29, 12, 0))
                .content(Files.readString(fellowShipOfTheRing))
                .numberOfLikes(2351457L)
                .build());

        Path twoTowers = Paths.get(getClass().getResource("/articles/twoTowers.txt").toURI());
        this.articleService.createArticle(Article.builder()
                .title("The Two Towers")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 11, 11, 12, 0))
                .content(Files.readString(twoTowers))
                .numberOfLikes(717321L)
                .build());

        Path returnOfTheKing = Paths.get(getClass().getResource("/articles/returnOfTheKing.txt").toURI());
        this.articleService.createArticle(Article.builder()
                .title("The Return of the king")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 10, 20, 12, 0))
                .content(Files.readString(returnOfTheKing))
                .numberOfLikes(678820L)
                .build());
    }

    /**
     * Creates initial users
     *
     * @throws URISyntaxException
     * @throws IOException
     */
    private void initUsers() {
        userService.createUser(User.builder()
                .username("frodo")
                .password("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918") // admin
                .birthdate(LocalDate.of(2980, 9, 22))
                .build()
        );
        userService.createUser(User.builder()
                .username("sam")
                .password("292bdd381b06dfb9fee4a0be10048bb6e8331963b3a4ec490ce0dab333468040") // potatoes
                .birthdate(LocalDate.of(2983, 5, 1))
                .build()
        );
        userService.createUser(User.builder()
                .username("merry")
                .password("65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5") // qwerty
                .birthdate(LocalDate.of(2982, 12, 2))
                .build()
        );
        userService.createUser(User.builder()
                .username("pippin")
                .password("0ca18fa26bd10ec5f20286a5364937b969c73a934b96610005c67cedb9d13ae3") // okoń
                .birthdate(LocalDate.of(2980, 3, 2))
                .build()
        );
    }
}
