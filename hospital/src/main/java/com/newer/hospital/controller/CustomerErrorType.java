package com.newer.hospital.controller;

public class CustomerErrorType {

    private String errorMessage;

    public CustomerErrorType() {
    }

    public CustomerErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
