package com.example.database.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class course {
    @Id
    private String course_id;

    private String course_name;
    private String teacher_Id;
    private String teacher_name;
    private String type;
    private String is_endClass;
    private int failTimes;
    private String department_cannot_learn;
    private String department_must_learn;
    private String content;


}