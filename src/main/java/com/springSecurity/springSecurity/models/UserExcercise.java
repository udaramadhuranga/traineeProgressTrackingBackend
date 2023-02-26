package com.springSecurity.springSecurity.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Optional;

@Data
@Document(collection = "usersExercises")
public class UserExcercise {

    @Id
    private String id;
    @DBRef
    User traineeId;

    @DBRef
    Exercise exercise;

    @JsonFormat(pattern="yyyy-MM-dd")
     Date Assined_Date;
    @JsonFormat(pattern="yyyy-MM-dd")
     Date Completed_Date;


    String status;

    String comment;

    public UserExcercise(User traineeId, Exercise exercise, Date assined_Date, Date completed_Date, String status, String comment) {
        this.traineeId = traineeId;
        this.exercise = exercise;
        Assined_Date = assined_Date;
        Completed_Date = completed_Date;
        this.status = status;
        this.comment = comment;
    }

    public UserExcercise(String id, User traineeId, Exercise exercise, Date assined_Date, Date completed_Date, String status, String comment) {
        this.id = id;
        this.traineeId = traineeId;
        this.exercise = exercise;
        Assined_Date = assined_Date;
        Completed_Date = completed_Date;
        this.status = status;
        this.comment = comment;
    }

    public UserExcercise() {
    }
}
