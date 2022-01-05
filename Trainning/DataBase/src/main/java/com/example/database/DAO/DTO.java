package com.example.database.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTO {

    private String course_name;

    private String type;
    private String department_cannot_learn;
    private String department_must_learn;
    private String content;
    private String staff_id;
    private String course_id;
    private String staff_name;
    private String department_name;
    //private String course_name ;
    private String log_name;
    private String operation;
    private String date;
    private String is_endClass;
    private int grade;
    private String graduation;
    private int gender;
    private int age;
    private String joiningTime;
    private String address;
    private String teachingTime;
    private String pass;


    private boolean isAdmin;

    public enum Role {
        teacher, normal, manager
    }

    private Role role;

    public DTO(String ID)
    {
        this.staff_id=ID;
    }

    public String getStaff_id(){
        return staff_id;
    }


    public Role getRole() {
        return Role.normal;
    }
}