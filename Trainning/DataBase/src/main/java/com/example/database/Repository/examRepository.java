package com.example.database.Repository;


import com.example.database.DAO.DTO;
import com.example.database.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class examRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public examRepository() {
    }


    //看成绩
    public boolean checkGarde(DTO dto) {
        List<exam> staffList = selectExams(dto);
        for (exam exam : staffList) {
            if (exam.getGraduation().equals("No")) {
                return false;
            }
        }
        return true;
    }

    public void uploadExamByDepartment(DTO dto) {
        if (dto.getCourse_id() != null) {
            List<staff> staffList = new staffRepository().selectStaffs(dto);
            for (int i = 0; i < staffList.size(); i++) {
                jdbcTemplate.update("insert into exam (staff_id, course_id, staff_name, course_name, is_endClass, grade, graduation) values(?, ?, ?, ?, ?, ?, ?)",
                        staffList.get(i).getId(), dto.getCourse_id(), staffList.get(i).getName(), dto.getCourse_name(), false, 0, "No");
            }
        }
        if (dto.getStaff_id() != null) {
            List<course> courseList = new courseRepository().selectCourses(dto);
            for (int i = 0; i < courseList.size(); i++) {
                jdbcTemplate.update("insert into exam (staff_id, course_id, staff_name, course_name, is_endClass, grade, graduation) values(?, ?, ?, ?, ?, ?, ?)",
                        dto.getStaff_id(), courseList.get(i).getCourse_id(), dto.getStaff_name(), courseList.get(i).getCourse_name(), false, 0, "No");
            }
        }


    }

    public void uploadExamByManager(DTO dto) {
        if (dto.getRole() == DTO.Role.manager) {
            DTO dto1 = new DTO();
            dto1.setDepartment_name(dto.getDepartment_name());
            List<course> courseList = new courseRepository().selectCourses(dto1);
            List<staff> staffList = new staffRepository().selectStaffsByDepartment(dto1);
            for (int i = 0; i < courseList.size(); i++) {
                jdbcTemplate.update("insert into exam (staff_id, course_id, staff_name, course_name, is_endClass, grade, graduation) values(?, ?, ?, ?, ?, ?, ?)",
                        staffList.get(i).getId(), courseList.get(i).getCourse_id(), staffList.get(i).getName(), courseList.get(i).getCourse_name(), false, 0, "No");
            }
        }
    }


    //打分
    public void modifyExam(DTO dto) throws Exception {
        if (dto.getGrade() != 0) {
            dto.setIs_endClass("是");
            if (dto.getGrade() > 60) {
                dto.setGraduation("Yes");
            } else {
                dto.setGraduation("No");
            }
            jdbcTemplate.update("UPDATE exam SET  is_endClass=?, grade=?, graduation=? WHERE staff_id=? and course_id=? and is_endClss='否'",
                    dto.getIs_endClass(), dto.getGrade(), dto.getGraduation(), dto.getStaff_id(), dto.getCourse_id());
        } else {
            throw new Exception();
        }
    }

    //删人、删课时删除考试
    public void deleteExam(DTO dto) {
        if (dto.getStaff_id() != null) {
            jdbcTemplate.update("DELETE FROM TABLE exam WHERE staff_id=?", dto.getStaff_id());
        }
        if (dto.getCourse_id() != null) {
            jdbcTemplate.update("DELETE FROM TABLE exam WHERE course_id=?", dto.getCourse_id());
        }
    }

    public List<exam> selectOnClass(DTO dto){//获取正在上的课程
        if (dto.getStaff_id() != null) {
            List<exam> list1 = jdbcTemplate.query("SELECT * FROM exam WHERE staff_id = ? and is_endClass = '否' ",
                    new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(exam.class));
            return list1;
        }
        return null;
    }

    public List<exam> selectClassHistory(DTO dto){//获取培训历史
        if (dto.getStaff_id() != null) {
            List<exam> list1 = jdbcTemplate.query("SELECT * FROM exam WHERE staff_id = ? and is_endClass = '是' ",
                    new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(exam.class));
            return list1;
        }
        return null;
    }


    public List<exam> selectExams(DTO dto) {
        if (dto.getStaff_id() != null) {
            List<exam> list1 = jdbcTemplate.query("SELECT * FROM exam WHERE staff_id = ?",
                    new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(exam.class));
            return list1;
        }
        if (dto.getType() != null) {
            List<exam> list1 = jdbcTemplate.query("SELECT * FROM exam WHERE type = ?",
                    new Object[]{dto.getType()}, new BeanPropertyRowMapper<>(exam.class));
            return list1;
        }
//        if (dto.is_endClass()) {
//            List<exam> list1 = jdbcTemplate.query("SELECT * FROM exam WHERE is_endClass = true",
//                    new BeanPropertyRowMapper<>(exam.class));
//
//            return list1;
//        }
        if (dto.getGraduation() != null) {
            List<exam> list1 = jdbcTemplate.query("SELECT * FROM exam WHERE graduation = ?",
                    new Object[]{dto.getGraduation()}, new BeanPropertyRowMapper<>(exam.class));
            return list1;
        }


        return null;
    }
}
