package scc.vigilancia.comunitaria.dto.New;

import lombok.Data;
@Data
public class NewUserRequest {
    private String email;
    private String name;
    private String password;
    private String permission;
}
