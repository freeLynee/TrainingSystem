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
public class staff {
    @Id
    private String id;

    private String name;

    private String code="2000";
    private String gender;
    private int age;
    private String joiningTime;
    private String address;
    private String department;
    private String phone;
    private String email;
    private String position;



}