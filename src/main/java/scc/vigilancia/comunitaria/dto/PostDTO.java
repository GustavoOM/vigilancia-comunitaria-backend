package scc.vigilancia.comunitaria.dto;

import lombok.Data;
import scc.vigilancia.comunitaria.models.Post;

@Data
public class PostDTO {
    private Integer id;

    private String emailAuthor;

    private String nameAuthor;

    private int idCommunity;

    private String nameCommunity;

    private String title;

    private String content;

    private String type;

    private String status;

    public PostDTO(Post post){
        id = post.getId();
        emailAuthor = post.getAuthor().getEmail();
        nameAuthor = post.getAuthor().getName();
        idCommunity = post.getCommunity().getId();
        nameCommunity = post.getCommunity().getName();
        title = post.getTitle();
        content = post.getContent();
        type = post.getType().name();
        status = post.getStatus().name();
    }

}






