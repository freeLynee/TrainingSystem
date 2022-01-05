package com.example.database.DAO;

public class Response {
    private String code;

    Response(String code)
    {
        this.code=code;
    }

    public String getCode(){
        return code;
    }
    public void setCode(String code){
        this.code=code;
    }
}
