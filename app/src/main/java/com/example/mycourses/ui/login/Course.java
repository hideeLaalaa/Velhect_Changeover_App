package com.example.mycourses.ui.login;

public class Course {

    private int xId;
    private String xCourse;
    private String xTitle;
    private int xUnit;
    private int xCarry;
    private String xLecturer;

    public Course(int xId, String xCourse, String xTitle, int xUnit, int xCarry, String xLecturer) {
        this.xId = xId;
        this.xCourse = xCourse;
        this.xTitle = xTitle;
        this.xUnit = xUnit;
        this.xCarry = xCarry;
        this.xLecturer = xLecturer;
    }

    public int getxId() {
        return xId;
    }

    public void setxId(int xId) {
        this.xId = xId;
    }

    public String getxCourse() {
        return xCourse;
    }

    public void setxCourse(String xCourse) {
        this.xCourse = xCourse;
    }

    public String getxTitle() {
        return xTitle;
    }

    public void setxTitle(String xTitle) {
        this.xTitle = xTitle;
    }

    public int getxUnit() {
        return xUnit;
    }

    public void setxUnit(int xUnit) {
        this.xUnit = xUnit;
    }

    public int getxCarry() {
        return xCarry;
    }

    public void setxCarry(int xCarry) {
        this.xCarry = xCarry;
    }

    public String getxLecturer() {
        return xLecturer;
    }

    public void setxLecturer(String xLecturer) {
        this.xLecturer = xLecturer;
    }
}
