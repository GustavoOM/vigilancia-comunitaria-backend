package scc.vigilancia.comunitaria.dto;

import lombok.Data;
@Data
public class NewUserRequest {
    private String email;
    private String name;
    private String password;
    private String permission;
}
