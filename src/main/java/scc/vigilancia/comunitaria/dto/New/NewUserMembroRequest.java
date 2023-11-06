package scc.vigilancia.comunitaria.dto.New;

import lombok.Data;

@Data
public class NewUserMembroRequest {
    private String email;
    private String name;
    private String password;
}
