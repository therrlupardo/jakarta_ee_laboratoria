package pl.edu.pg.eti.kask.blog.user.dto;

import lombok.*;
import pl.edu.pg.eti.kask.blog.user.entity.User;

/**
 * @author mateusz.buchajewicz
 * DTO for User class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    private Long id;
    private String name;

    public static UserDto convertFromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUsername())
                .build();
    }
}
