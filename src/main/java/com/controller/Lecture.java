
package com.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;

public class Lecture extends Course {
    private String crn;
    private String section;
    private String courseId;
    private String courseType;
    private String professor;
    private String professorFirstName;
    private String professorMiddleName;
    private String professorLastName;
    private String location;
   

    /**
     * constructor
     * also breaks up professor name in firstName, middleName, and Lastname --> easy to find ratings
     * if professor name --> TBA then leaves everything "" and gives avg rating of 5/10
     * @param crn
     * @param section
     * @param courseId
     * @param courseType
     * @param professor
     * @param location
     * @param time
     * @param days
     */

    public Lecture (String crn, String section, String courseId, String courseType, String professor, String location, String time, String days, String term) {
        super(crn, section, courseId, courseType, professor, location, time, days, term);
        this.professor = professor;
        this.courseId = courseId.replaceAll(" ","");

        if (!professor.equals("TBA")) {
            professorFirstName = professor.substring(0, professor.indexOf(" "));
            String tempName = professor.substring(professor.indexOf(" ") + 1);
            int count = 0;
            try {
                String tempName2 = tempName.substring(tempName.indexOf(" ") + 1);
                count = count + 1;
                tempName2.substring(tempName2.indexOf(" "));
                count = count + 1;
                professorMiddleName = tempName.substring(0, tempName.indexOf(" "));
                professorLastName = tempName2.substring(0, tempName2.indexOf(" "));
            } catch (RuntimeException ex) {
                professorMiddleName = "";
                professorLastName = tempName.substring(0, tempName.indexOf(" "));
            }
        } else {
            professorFirstName = "";
            professorLastName = "";
            professorMiddleName = "";
        }
    }




    /**
     * method that returns prof rating by taking avg of Rate my prof and avg GPA (50-50) weight
     * if ratemyprof no exist multiples gpa by 10/4
     * if gpa no exist multiples ratemyprof rating by 2
     * if none exist avg rating default = 5
     */
 
    
    
    
    
   // trying
    
    public Double rmpRating() {
    	Double rateMyProfRating = null;
    	for (String[] data: RateMyProf.tempprofessorlist){
    		
    		if (professorFirstName.indexOf("-") != -1) {
        		if (data[0].indexOf(professorFirstName.substring(0,professorFirstName.indexOf("-"))) != -1 && data[2].equals(professorLastName)) {
                    if (!data[3].equals("N/A")) {
                    	rateMyProfRating = Double.parseDouble(data[3]);
                    }
        		}
        } else {
        	if (data[0].equals(professorFirstName) && data[2].equals(professorLastName)) {
                if (!data[3].equals("N/A")) {
                    rateMyProfRating = Double.parseDouble(data[3]);
                    
                }
            }
        }
        	
        }
    	return rateMyProfRating;
    }
       
    
    public Double GPAgetter() {
    	ArrayList<String[]> profGPA = new ArrayList<String[]>();  	
    	CourseCritique b = new CourseCritique(courseId.substring(0, courseId.length()-4), courseId.substring(courseId.length()-4));
    try {
        b.profAvgGpaGenerator();
    }
    catch(Exception ex){
    	System.out.println("GPAERRR");
    }
    profGPA.addAll(b.getNameRating());
    
    String temporaryName;
	Double courseCritiqueRating = null;
    if (professorMiddleName.equals("")){
    	temporaryName = professorLastName+", "+professorFirstName;
    } else {
    	temporaryName = professorLastName+", "+professorFirstName+" "+professorMiddleName;
    }
    for(String[] data: profGPA ) {
        if (data[0].equals(temporaryName)) {
            courseCritiqueRating = Double.parseDouble(data[1]);
        }
    }
    return courseCritiqueRating;
    
    }
    /*
    
    private Double rmpRating = rmpRating();
    private Double Gpa = GPAgetter();
    
    public double lectureRating() {
    	if (rmpRating == null && Gpa == null) {
            return 5.0;
        } else if (rmpRating == null && Gpa != null) {
            return Gpa*(10.0/4);
        } else if (rmpRating != null && Gpa == null) {
            return rmpRating*1.6;
        } else {
            return (rmpRating + Gpa*(5.0/4));
        }
    }
    

    public Double rmpRatingGetter() {
    	return rmpRating();
    }
    public Double GpaGetter() {
    	return Gpa;
    }
    */		
   // 
    
    public double lectureRating (ArrayList<String[]> profData) throws IOException { // replace ioexpeception for m2 with another exception
    	if (professorFirstName.equals("")){
    		return 5.0;
    	}
    	String temporaryName;
    	Double courseCritiqueRating = null;
        Double rateMyProfRating = null;
        if (professorMiddleName.equals("")){
        	temporaryName = professorLastName+", "+professorFirstName;
        } else {
        	temporaryName = professorLastName+", "+professorFirstName+" "+professorMiddleName;
        }
        for(String[] data: profData ) {
            if (data[0].equals(temporaryName)) {
                courseCritiqueRating = Double.parseDouble(data[1]);
            }
        }

        for (String[] data: RateMyProf.tempprofessorlist){
        	if (professorFirstName.indexOf("-") != -1) {
        		if (data[0].indexOf(professorFirstName.substring(0,professorFirstName.indexOf("-"))) != -1 && data[2].equals(professorLastName)) {
        	
                    if (!data[3].equals("N/A")) {
                        rateMyProfRating = Double.parseDouble(data[3]);
                        
                    }
                }
        		
        	}else {
            if (data[0].equals(professorFirstName) && data[2].equals(professorLastName)) {
                if (!data[3].equals("N/A")) {
                    rateMyProfRating = Double.parseDouble(data[3]);
                    
                }
            }
        	}
        }

        if (rateMyProfRating == null && courseCritiqueRating == null) {
            return 5.0;
        } else if (rateMyProfRating == null && courseCritiqueRating != null) {
            return courseCritiqueRating*(10.0/4);
        } else if (rateMyProfRating != null && courseCritiqueRating == null) {
            return rateMyProfRating*1.6;
        } else {
            return (rateMyProfRating + courseCritiqueRating*(5.0/4));
        }

      
    }
    

}

