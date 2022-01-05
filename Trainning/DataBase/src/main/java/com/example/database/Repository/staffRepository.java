package com.example.database.Repository;


import com.example.database.DAO.DTO;
import com.example.database.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class staffRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public staff uploadStaff(DTO dto) {

        String sql = "insert into staff (id, name, age, department) values(?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                dto.getStaff_id(), dto.getStaff_name(), dto.getAge(), dto.getDepartment_name());
        DTO dto1 = new DTO();
        dto1.setDepartment_must_learn(dto.getDepartment_name());
        dto1.setStaff_id(dto.getStaff_id());
        dto1.setStaff_name(dto.getStaff_name());
        new examRepository().uploadExamByDepartment(dto1);

        return null;

    }


    //insert教员或主管
    public staff uploadSpecialStaff(DTO dto) {
        if (dto.getRole() == DTO.Role.manager) {
            jdbcTemplate.update("insert into manager (id, name, department) values(?, ?, ?)",
                    dto.getStaff_id(), dto.getStaff_name(), dto.getDepartment_name());
        }
        if (dto.getRole() == DTO.Role.teacher) {
            jdbcTemplate.update("insert into teacher (id, name, teachingTime) values(?, ?, ?)",
                    dto.getStaff_id(), dto.getStaff_name(), dto.getTeachingTime());
            new courseRepository().modifyCourse(dto);
        }
        return selectStaffs(dto).get(0);
    }

    public List<staff> selectStaffs(DTO dto) {
        staff staff1 = new staff();
        String sql = "select * from staff where id = ?";
        if (dto.getStaff_id() != null) {
            if (dto.getRole() == DTO.Role.normal || dto.getRole() == null) {
                List<staff> list = jdbcTemplate.query("SELECT * FROM staff WHERE ID = ?",
                        new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(staff.class));
                if (list != null && list.size() > 0) {
                    staff staff2 = list.get(0);

                    return list;
                } else {
                    return null;
                }
            } else if (dto.getRole() == DTO.Role.manager) {
                List<staff> list1 = jdbcTemplate.query("SELECT * FROM manager WHERE id = ?",
                        new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(staff.class));
                if (list1 != null && list1.size() > 0) {
                    staff staff2 = list1.get(0);
                    return list1;

                } else {
                    return null;
                }
            } else if (dto.getRole() == DTO.Role.teacher) {
                List<staff> list3 = jdbcTemplate.query("SELECT * FROM teacher WHERE id = ?",
                        new Object[]{dto.getStaff_id()}, new BeanPropertyRowMapper<>(staff.class));
                if (list3 != null && list3.size() > 0) {
                    staff staff3 = list3.get(0);
                    return list3;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else if (dto.getStaff_name() != null) {
            List<staff> list = jdbcTemplate.query("SELECT * FROM staff WHERE name = ?",
                    new Object[]{dto.getStaff_name()}, new BeanPropertyRowMapper<>(staff.class));
            if (list != null && list.size() > 0) {
                staff staff2 = list.get(0);
                return list;
            } else {
                return null;
            }

        }

        return null;
    }

    public List<staff> selectStaffsByDepartment(DTO dto) {
        String departemnt = dto.getDepartment_name();
        staff staff1 = new staff();
        String sql = "select * from staff where id = ?";
        if (departemnt != null) {
            List<staff> list = jdbcTemplate.query("SELECT * FROM staff WHERE dept = ?",
                    new Object[]{departemnt}, new BeanPropertyRowMapper<>(staff.class));
            if (list != null && list.size() > 0) {
                staff staff2 = list.get(0);
                return list;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public staff modifyStaff(DTO dto) {
        staff staff1 = new staff();
        if (dto.getRole() == DTO.Role.normal || dto.getRole() == null) {
            if (!dto.getDepartment_name().equals(selectStaffs(dto).get(0).getDepartment()) && !new examRepository().checkGarde(dto)) {
                //考试不合格，不允许转部门
                return null;
            }
            jdbcTemplate.update("UPDATE staff SET  name=?, age=?, department=? WHERE id=?",
                    dto.getStaff_name(), dto.getAge(), dto.getDepartment_name(), dto.getStaff_id());

            return selectStaffs(dto).get(0);
        } else if (dto.getRole() == DTO.Role.manager) {
            jdbcTemplate.update("UPDATE staff SET  name=?, age=?,  WHERE id=?",
                    dto.getStaff_name(), dto.getAge(), dto.getStaff_id());
            jdbcTemplate.update("UPDATE manager SET  name=?,  WHERE id=?",
                    dto.getStaff_name(), dto.getStaff_id());

            return selectStaffs(dto).get(0);
        } else if (dto.getRole() == DTO.Role.teacher) {
            jdbcTemplate.update("UPDATE staff SET  name=?, age=?, department=? WHERE id=?",
                    dto.getStaff_name(), dto.getAge(), dto.getDepartment_name(), dto.getStaff_id());
            jdbcTemplate.update("UPDATE teacher SET  name=?, teachingTime=? WHERE id=?",
                    dto.getStaff_name(), dto.getStaff_name(), dto.getStaff_id());
            return selectStaffs(dto).get(0);

        } else {
            return null;
        }
    }

    public void deleteStaff(DTO dto) {
        jdbcTemplate.update("DELETE FROM TABLE staff WHERE id=?", dto.getStaff_id());
        DTO dto1 = new DTO();
        dto1.setStaff_id(dto.getCourse_id());
        new examRepository().deleteExam(dto1);
    }
}