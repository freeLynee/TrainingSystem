package com.example.database.Repository;

import com.example.database.DAO.DTO;
import com.example.database.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class logRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String upload() {
        //TODO
        return null;
    }

    public List<log> selectLogs(DTO dto) {
        if (dto.isAdmin()) {

            String sql = "select * from log ";

            List<log> list = jdbcTemplate.query(sql,
                    new BeanPropertyRowMapper<>(log.class));

            return list;
        } else {
            String sql = "select * from log where id = ?";
            List<log> list = jdbcTemplate.query(sql, new Object[]{dto.getStaff_id()},
                    new BeanPropertyRowMapper<>(log.class));

            return list;
        }
    }
}