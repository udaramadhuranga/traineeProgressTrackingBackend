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
    String traineeId;


    String exercise;

    Date Assined_Date;

    Date Completed_Date;

    @Value("${some.key:Not Completed}")
    String status;

    String comment;
}
