package com.twd.SpringSecurityJWT.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDTO {
    private int id;
    private String name;
    private String description;
    private LocalDateTime estimatedEndtime;
    private LocalDateTime endtime;
    private String status;
    private String priority;
    private int projectId;
    private int userId;
}