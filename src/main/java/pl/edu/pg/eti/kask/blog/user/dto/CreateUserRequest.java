package pl.edu.pg.eti.kask.blog.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.user.entity.User;
import pl.edu.pg.eti.kask.blog.utils.Sha256HashingUtility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author mateusz.buchajewicz
 *
 * POST user request, has only fields requeired to create user
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserRequest {
    /**
     * User's username
     */
    private String username;

    /**
     * User's password
     */
    private String password;

    /**
     * Birthdate as string in format YYYY-MM-DD
     */
    private String birthdate;

    /**
     * Converts model to entity
     * @param request model to be converted
     * @return converted entity
     */
    public static User convertToEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(Sha256HashingUtility.hash(request.getPassword()))
                .birthdate(LocalDate.parse(request.getBirthdate()))
                .build();
    }
}
