package pl.edu.pg.eti.kask.blog.configuration;

import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.blog.article.entity.Article;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.user.entity.UserRoles;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 * Initializes data
 */
@Singleton
@Startup
@NoArgsConstructor
public class InitializeData {
    private EntityManager em;
    private Pbkdf2PasswordHash pbkdf;


    @PersistenceContext
    public void setEm(EntityManager em) { this.em = em; }


    @Inject
    public InitializeData(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    /**
     * Creates initial data
     *
     * @throws URISyntaxException thrown if resource URI is not valid
     * @throws IOException thrown if any input/output exception
     */
    @PostConstruct
    @Transactional
    private synchronized void init() throws URISyntaxException, IOException {
        User kevin = User.builder()
                .username("kevin")
                .password(pbkdf.generate("useruser".toCharArray()))
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();
        em.persist(kevin);
        User frodo = User.builder()
                .username("frodo")
                .password(pbkdf.generate("admin".toCharArray()))
                .birthdate(LocalDate.of(2980, 9, 22))
                .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                .build();
        User sam = User.builder()
                .username("sam")
                .password(pbkdf.generate("potatoes".toCharArray()))
                .birthdate(LocalDate.of(2983, 5, 1))
                .roles(List.of(UserRoles.USER))
                .build();
        User merry = User.builder()
                .username("merry")
                .password(pbkdf.generate("qwerty".toCharArray()))
                .birthdate(LocalDate.of(2982, 12, 2))
                .roles(List.of(UserRoles.USER))
                .build();
        User pippin = User.builder()
                .username("pippin")
                .password(pbkdf.generate("okoń".toCharArray()))
                .birthdate(LocalDate.of(2980, 3, 2))
                .roles(List.of(UserRoles.USER))
                .build();

        em.persist(frodo);
        em.persist(sam);
        em.persist(merry);
        em.persist(pippin);

        Path fellowShipOfTheRing = Paths.get(getClass().getResource("/articles/fellowshipOfTheRing.txt").toURI());
        Article the_fellowship_of_the_ring = Article.builder()
                .title("The Fellowship of the ring")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 7, 29, 12, 0))
                .content(Files.readString(fellowShipOfTheRing))
                .numberOfLikes(2351457L)
                .build();

        Path twoTowers = Paths.get(getClass().getResource("/articles/twoTowers.txt").toURI());
        Article the_two_towers = Article.builder()
                .title("The Two Towers")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 11, 11, 12, 0))
                .content(Files.readString(twoTowers))
                .numberOfLikes(717321L)
                .build();


        Path returnOfTheKing = Paths.get(getClass().getResource("/articles/returnOfTheKing.txt").toURI());
        Article the_return_of_the_king = Article.builder()
                .title("The Return of the king")
                .author("J.R.R.Tolkien")
                .creationTime(LocalDateTime.of(1954, 10, 20, 12, 0))
                .content(Files.readString(returnOfTheKing))
                .numberOfLikes(678820L)
                .build();

        em.persist(the_fellowship_of_the_ring);
        em.persist(the_two_towers);
        em.persist(the_return_of_the_king);

        Comment comment1 = Comment.builder()
                .article(the_fellowship_of_the_ring)
                .user(frodo)
                .content("Best book ever!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(5689321L)
                .build();
        em.persist(comment1);
        frodo.setComments(List.of(comment1));

        Comment comment2 = Comment.builder()
                .article(the_fellowship_of_the_ring)
                .user(sam)
                .content("Damn Nazguls, we couldn't event eat potatoes!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(1234L)
                .build();
        em.persist(comment2);
        sam.setComments(List.of(comment2));

        Comment comment3 = Comment.builder()
                .article(the_fellowship_of_the_ring)
                .user(merry)
                .content("@Pippin, look at that!")
                .creationTime(LocalDateTime.now())
                .numberOfLikes(-12L)
                .build();
        em.persist(comment3);

        merry.setComments(List.of(comment3));

        the_fellowship_of_the_ring.setComments(List.of(comment1, comment2, comment3));
    }
}
