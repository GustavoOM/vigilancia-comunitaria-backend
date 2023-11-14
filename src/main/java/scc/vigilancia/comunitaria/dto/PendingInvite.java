package scc.vigilancia.comunitaria.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PendingInvite {

    private String communityName;
    private String userName;
    private String status;
}
