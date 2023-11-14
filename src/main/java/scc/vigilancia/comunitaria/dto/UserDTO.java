package scc.vigilancia.comunitaria.dto;

import lombok.Data;
import scc.vigilancia.comunitaria.models.Image;
import scc.vigilancia.comunitaria.models.User;


@Data
public class UserDTO {
    private String email;
    private String name;
    private String permission;

    public UserDTO(User user) {
        email = user.getEmail();
        name = user.getName();
        permission = user.getPermission().name();
    }

}






