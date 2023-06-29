package com.example.ZoneZero_furkanreyhan;

public class Doctor {
    private final String fullName;
    private final String status;
    private final String gender;
    private final String imageUrl;

    public Doctor(String fullName, String status, String gender, String imageUrl) {
        this.fullName = fullName;
        this.status = status;
        this.gender = gender;
        this.imageUrl = imageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStatus() {
        return status;
    }

    public String getGender() {
        return gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
