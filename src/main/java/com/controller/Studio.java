package com.controller;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
public class Studio extends Course {

    private String crn;
    private String section;
    private String courseId;
    private String courseType;
    private String professor;
    private String location;

    public Studio (String crn, String section, String courseId, String courseType, String professor, String location, String time, String days, String term) {
        super(crn, section, courseId, courseType, professor, location, time, days, term);
        this.courseId = courseId.replaceAll(" ","");

    }

}

