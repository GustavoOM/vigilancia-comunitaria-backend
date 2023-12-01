package scc.vigilancia.comunitaria.dto;

import lombok.Data;
import scc.vigilancia.comunitaria.models.Community;
import scc.vigilancia.comunitaria.models.Image;
import scc.vigilancia.comunitaria.models.Post;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommunityDTO {
    private Integer id;
    private String name;

    public CommunityDTO(Community community){
        this.id = community.getId();
        this.name = community.getName();
    }

    public CommunityDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}






