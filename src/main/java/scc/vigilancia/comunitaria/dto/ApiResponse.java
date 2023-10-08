package scc.vigilancia.comunitaria.dto;


import lombok.Builder;
import lombok.Data;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
public class ApiResponse {

    private String code;
    private String message;
    private String date;

    public ApiResponse(String code, String message, String date) {
        this.code = code;
        this.message = message;
        LocalDateTime now = LocalDateTime.now(Clock.systemDefaultZone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.date = now.format(formatter);
    }

}
