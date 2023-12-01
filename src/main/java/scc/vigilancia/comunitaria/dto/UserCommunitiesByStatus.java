package scc.vigilancia.comunitaria.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class UserCommunitiesByStatus {

    private List<CommunityDTO> approved;
    private List<CommunityDTO> pending;
    private List<CommunityDTO> available;

    public UserCommunitiesByStatus(List<CommunityDTO> approved, List<CommunityDTO> pending, List<CommunityDTO> available) {
        this.approved = approved;
        this.pending = pending;
        this.available = available;
    }

    public UserCommunitiesByStatus() {
        this.approved = new ArrayList<>();
        this.pending = new ArrayList<>();
        this.available = new ArrayList<>();
    }
}
