package com.example.database.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class exam implements Serializable {
    @Id
    private String staff_id, course_id;
    private String staff_name;

    private String course_name;
    private String is_endClass;
    private int grade;
    private String graduation;


}