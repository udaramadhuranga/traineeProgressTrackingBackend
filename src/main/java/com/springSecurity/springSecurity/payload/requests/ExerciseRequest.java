package com.springSecurity.springSecurity.payload.requests;

import com.springSecurity.springSecurity.models.UserCourse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExerciseRequest {


    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String discription;


    private List<String> tasks;

    private List<Object> assignedUsers;



}
