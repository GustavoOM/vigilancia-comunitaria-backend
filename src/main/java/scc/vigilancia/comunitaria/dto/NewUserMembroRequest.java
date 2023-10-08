package scc.vigilancia.comunitaria.dto;

import lombok.Data;

@Data
public class NewUserMembroRequest {
    private String email;
    private String name;
    private String password;
}
