package com.example.hrindex.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

public class CustomErrorResponse {

    private int status;
    private String msg;
    private List<String> data;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(int status, String msg, List<String> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getdata() {
        return data;
    }

    public void setdata(List<String> keyData) {
        this.data = data;
    }
}
