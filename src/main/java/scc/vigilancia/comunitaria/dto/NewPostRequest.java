package scc.vigilancia.comunitaria.dto;

import lombok.Data;
@Data
public class NewPostRequest {

    private Integer idCommunity;

    private String title;

    private String content;

    private String type;

    private String status;
}
