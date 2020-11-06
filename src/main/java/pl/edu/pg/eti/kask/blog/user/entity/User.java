package pl.edu.pg.eti.kask.blog.user.entity;

import lombok.*;
import pl.edu.pg.eti.kask.blog.comment.entity.Comment;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author mateusz.buchajewicz
 * Entity for system user
 * Represents information about particular user, including his credentials.
 */
@Data
@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable {
    /**
     * User's id
     */
//    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * User's username
     */
    @Id
//    @Column(unique = true)
    private String username;

    /**
     * User's password, hashed with {@link Sha256HashingUtility}
     */
    @ToString.Exclude
    private String password;

    /**
     * User's date of birth
     */
    private LocalDate birthdate;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;

//    public String getAvatarFileName() {
//        return id + ".png";
//    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    /**
     * List of user's roles
     */
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;
}
