package com.springSecurity.springSecurity.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
public class UserCourse {

    @DBRef
    User traineeId;

    @Value("${some.key:Not Completed}")
    String status;
}
