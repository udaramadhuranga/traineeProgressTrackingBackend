package com.springSecurity.springSecurity.payload.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcersiceResponse {

    private String title;


    private String description;


    private List<String> tasks;


    private int maximum_time;

}
