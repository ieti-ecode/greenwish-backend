package edu.eci.ieti.greenwish.models.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorDto {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
