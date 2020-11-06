package pl.edu.pg.eti.kask.blog.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.user.entity.User;

import java.util.List;

/**
 * @author mateusz.buchajewicz
 * DTO for User class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {

    /**
     * Unique user identifier
     */
    private Long id;

    /**
     * User's name
     */
    private String name;

    /**
     * User's roles
     */
    private List<String> roles;

    /**
     * Converts User to UserDto
     *
     * @param user entity to be converted
     * @return converted model
     */
    public static UserDto convertFromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
