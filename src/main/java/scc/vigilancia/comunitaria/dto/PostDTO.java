package scc.vigilancia.comunitaria.dto;

import lombok.Data;
import scc.vigilancia.comunitaria.models.Image;
import scc.vigilancia.comunitaria.models.Post;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostDTO {
    private Integer id;

    private String emailAuthor;

    private String nameAuthor;

    private int idCommunity;

    private String nameCommunity;

    private String content;

    private String type;

    private String status;

    private Timestamp createdAt;

    private List<String> images = new ArrayList<>();

    public PostDTO(Post post) {
        id = post.getId();
        emailAuthor = post.getAuthor().getEmail();
        nameAuthor = post.getAuthor().getName();
        idCommunity = post.getCommunity().getId();
        nameCommunity = post.getCommunity().getName();
        content = post.getContent();
        type = post.getType().name();
        status = post.getStatus().name();
        createdAt = post.getCreatedAt();
        for (Image image : post.getImages()) {
            images.add(image.getCaminhoS3());
        }
    }

}






