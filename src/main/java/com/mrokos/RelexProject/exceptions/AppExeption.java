package com.mrokos.RelexProject.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class AppExeption {
    private int status;
    private String message;
    private Date timeStamp;

    public AppExeption(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = new Date();
    }
}
