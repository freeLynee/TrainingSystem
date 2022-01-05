package com.example.database.Repository;

import com.example.database.DAO.DTO;
import com.example.database.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class courseRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private courseRepository courseRepository;

    public courseRepository() {
    }

    public courseRepository(com.example.database.Repository.courseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    public course uploadCourse(DTO dto) {
        String must;
        jdbcTemplate.update("insert into course (id, name, teacher_Id , teacher_name, type, department_cannot_learn, department_must_learn, content) values(?, ?, ?, ?, ?, ?, ?, ?)",
                dto.getCourse_id(), dto.getCourse_name(), dto.getStaff_id(), dto.getStaff_name(), dto.getType(), dto.getDepartment_cannot_learn(), dto.getDepartment_must_learn(), dto.getContent());
        if ((must = selectCourses(dto).get(0).getDepartment_must_learn()) != null) {
            DTO dto1 = new DTO();
            dto1.setCourse_id(dto.getDepartment_name());
            dto1.setCourse_id(dto.getCourse_id());
            dto1.setCourse_name(dto.getCourse_name());
            new examRepository().uploadExamByDepartment(dto1);
        }
        return null;
    }

    public void deleteCourse(DTO dto) {
        jdbcTemplate.update("DELETE FROM TABLE course WHERE id=?", dto.getCourse_id());
        DTO dto1 = new DTO();

        jdbcTemplate.update("DELETE FROM TABLE teacher WHERE id=?", dto.getStaff_id());
        dto1.setCourse_id(dto.getCourse_id());
        new examRepository().deleteExam(dto1);
    }

    public List<course> getAllCourses(DTO dto) {
        if (dto.getDepartment_name() != null) {
            List<course> list = jdbcTemplate.query("SELECT * FROM course WHERE department = ?",
                    new Object[]{dto.getDepartment_name()}, new BeanPropertyRowMapper<>(course.class));
            if (list.size() > 0) {
                return list;
            }
        }
        return null;
    }


    public List<course> selectCourses(DTO dto) {
        if (dto.getCourse_id() != null) {
            List<course> list = jdbcTemplate.query("SELECT * FROM course WHERE id = ?",
                    new Object[]{dto.getCourse_id()}, new BeanPropertyRowMapper<>(course.class));
            if (list != null && list.size() > 0) {
                course course1 = list.get(0);
                return list;
            } else {
                return null;
            }
        }else if (dto.getStaff_id() != null) {
            List<course> list = jdbcTemplate.query("SELECT * FROM course WHERE teacher_id = ?",
                    new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(course.class));
            if (list != null && list.size() > 0) {
                return list;
            } else {
                return null;
            }

        } else if (dto.getCourse_name() != null) {
            List<course> list = jdbcTemplate.query("SELECT * FROM staff WHERE name = ?",
                    new Object[]{dto.getCourse_name()}, new BeanPropertyRowMapper<>(course.class));
            if (list != null && list.size() > 0) {

                return list;
            } else {
                return null;
            }

        } else if (dto.getDepartment_must_learn() != null) {
            List<course> list = jdbcTemplate.query("SELECT * FROM staff WHERE id is not null ",
                    new BeanPropertyRowMapper<>(course.class));
            List<course> courseList = new ArrayList<>();
            for (com.example.database.Entity.course course : list) {
                String[] courses = util(course.getDepartment_must_learn());
                for (String cours : courses) {
                    if (cours.equals(dto.getDepartment_must_learn())) {
                        courseList.add(course);
                    }
                }
            }

            return courseList;
        } else if (dto.getDepartment_name() != null) {
            List<course> list = jdbcTemplate.query("SELECT * FROM staff WHERE id is not null ",
                    new BeanPropertyRowMapper<>(course.class));
            List<course> courseList = new ArrayList<>();

            for (com.example.database.Entity.course course : list) {
                String[] must = util(course.getDepartment_must_learn());
                String[] not = util(course.getDepartment_cannot_learn());
                for (String cours : must) {
                    for (String courss : not)
                        if (!cours.equals(dto.getDepartment_name()) && !courss.equals(dto.getDepartment_name())) {
                            courseList.add(course);
                        }
                }
            }
            return courseList;
        } else {
            return null;
        }
    }

    public course modifyCourse(DTO dto) {
        if (dto.getCourse_id() == null) {
            return null;
        }
        if (dto.getStaff_id() != null) {
            //修改教员
            jdbcTemplate.update("UPDATE course SET teacher_Id=?, teacher_name=?  WHERE id=?",
                    dto.getStaff_id(), dto.getStaff_name(), dto.getCourse_id());
        }

        if (dto.getDepartment_cannot_learn() != null) {
            //修改cannot
            jdbcTemplate.update("UPDATE course SET department_cannot_learn=?  WHERE id=?",
                    dto.getDepartment_cannot_learn(), dto.getCourse_id());
        }


        if (dto.getDepartment_must_learn() != null) {
            //修改must
            jdbcTemplate.update("UPDATE course SET department_must_learn=?  WHERE id=?",
                    dto.getDepartment_must_learn(), dto.getCourse_id());
            new examRepository().uploadExamByDepartment(dto);
        }

        if (dto.getType() != null) {
            //修改type与内容（同时）
            jdbcTemplate.update("UPDATE course SET type=?, content=?  WHERE id=?",
                    dto.getType(), dto.getContent(), dto.getCourse_id());
        }
        return null;
    }

    public String[] util(String departments) {
        return departments.split("，");
    }
}