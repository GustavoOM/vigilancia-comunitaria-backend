package scc.vigilancia.comunitaria.dto.New;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewPostRequest {

    private Integer idCommunity;

    private String content;

    private String type;

    private String status;
}
