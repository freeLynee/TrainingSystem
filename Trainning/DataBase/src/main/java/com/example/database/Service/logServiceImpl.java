package com.example.database.Service;

import com.example.database.Repository.*;
import com.example.database.DAO.DTO;
import org.springframework.stereotype.Service;

@Service
public class logServiceImpl implements adminService {
    private final logRepository repository;

    public logServiceImpl(logRepository repository) {
        this.repository = repository;
    }

    @Override
    public void upload(DTO dto) {

    }

    @Override
    public void delete(DTO dto) {

    }
}