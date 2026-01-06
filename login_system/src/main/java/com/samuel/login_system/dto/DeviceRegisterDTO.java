package com.samuel.login_system.dto;

public class DeviceRegisterDTO {
    private String name;
    private String status;

    public DeviceRegisterDTO() {
    }

    public DeviceRegisterDTO(String name, String status) {
        this.name = name;
        this.status = status;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}