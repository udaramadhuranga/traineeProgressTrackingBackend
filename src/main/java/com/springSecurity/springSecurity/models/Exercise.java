package com.springSecurity.springSecurity.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "exercises")
public class Exercise {
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;


    private List<String> tasks;



    private int maximum_time;

    public Exercise(String title, String description, List<String> tasks, int maximum_time) {
        this.title = title;
        this.description = description;
        this.tasks = tasks;
        this.maximum_time = maximum_time;
    }
}

