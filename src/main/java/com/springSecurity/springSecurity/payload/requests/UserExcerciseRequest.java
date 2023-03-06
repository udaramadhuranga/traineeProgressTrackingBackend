package com.springSecurity.springSecurity.payload.requests;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class UserExcerciseRequest {

    @Id
    private String id;
    private String traineeId;

    private String exercise;

    private Date Assined_Date;

    private Date Completed_Date;

    @Value("${some.key:Not Completed}")
    private String status;

    private String comment;
}
