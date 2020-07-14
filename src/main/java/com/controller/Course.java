package com.controller;
import java.util.ArrayList;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import java.io.IOException;
public class Course {

    // class time, days, loc, start and end(date), type (lecture/studio), prof, sec, crn, course(CS3600)
    private String crn;
    private String section;
    private String courseId;
    private String courseType;
    private String professor;
    private String location;
    private String time;
    private String days;
    private int startTime;
    private int endTime;
    private String term;


    // days and  time data type
    private ArrayList<int[]> dayTime = new ArrayList<>(); //int[] contains of starttime and endtime for the course
    // int[] is in format that a course at 1000am:1100am on MW would be [11000, 11100], [31000,31100]
    // done to ensure even if course at same time but on diff days could be accepted

    /**
     * constructor
     * creates dayTime array for the course
     *
     * @param crn
     * @param section
     * @param courseId
     * @param courseType
     * @param professor
     * @param location
     * @param time
     * @param days
     */

    public Course (String crn, String section, String courseId, String courseType, String professor, String location, String time, String days, String term) {
    	
    	this.crn = crn;
        this.section = section;
        this.courseId = courseId.replaceAll(" ","");
        this.courseType = courseType;
        this.professor = professor;
        this.location = location;
        this.time = time;
        this.days = days;
        this.term = term;


        String startHours = time.substring(0,time.indexOf(":"));
        String startMinutes = time.substring(time.indexOf(":")+1,time.indexOf(":")+3);
        String starter = time.substring(time.indexOf(":")+4,time.indexOf(":")+4); // am or pm
        time = time.substring(time.indexOf(":")+4);
        String endHours = time.substring(time.indexOf("-")+2,time.indexOf(":"));
        String endMinutes = time.substring(time.indexOf(":")+1,time.indexOf(":")+3);
        String enderer = time.substring(time.indexOf(":")+4,time.indexOf(":")+4);


        if (starter.equals("am")) {
            startTime = Integer.parseInt(startHours+startMinutes);
        } else if (startHours.equals("12")) {
            startTime = Integer.parseInt(startHours+startMinutes);
        } else {
            startTime = Integer.parseInt(startHours+startMinutes) + 1200;
        }


        if (enderer.equals("am")) {
            endTime = Integer.parseInt(endHours+endMinutes);
        } else if(endHours.equals("12")) {
            endTime = Integer.parseInt(endHours+endMinutes);
        } else {
            endTime = Integer.parseInt(endHours+endMinutes) + 1200;
        }


        int dayNumber;
        for (char day : days.toCharArray()) {

            if (day == 'M'){
                dayNumber = 10000;
            } else if( day == 'T') {
                dayNumber = 20000;
            } else if (day == 'W') {
                dayNumber = 30000;
            } else if (day == 'R') {
                dayNumber = 40000;
            } else if(day == 'F') {
                dayNumber = 50000;
            } else {
                throw new RuntimeException();
            }
            int[] temp = {startTime + dayNumber, endTime + dayNumber};
            this.dayTime.add(temp);
            // M = 10000, T = 20000, W=30000, R=40000, F=50000
        }
    }

    /**
     * getter method for dayTime
     * @return
     */
    public ArrayList<int[]>  getDayTime(){
        return this.dayTime;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSection() {
        return this.section;
    }

    public String getCrn(){
        return crn;
    }
    
    public String getProfessor() {
    	return professor;
    }
    public String getDays() {
    	return days;
    }
    
    public String getTime() {
    	return time;
    }
    
    public int getStartTime(){
        return startTime;
    }

    public int getEndTime(){
        return endTime;
    }
    
    public String getCourseType() {
    	return courseType;
    }


    
    
    /**
     * checks if two courses have same day/time , if they do returns true else false
     * we want the return to be false so that they do not clash
     * @param other Course obj
     * @return bool if clash or not
     */
    public boolean clash (Course other) {
        for (int[] e: dayTime){
            for(int[] f: other.getDayTime()) {
                if ((e[0] <= f[0] && f[0] <= e[1])  || (e[0] <= f[1] && f[1] <= e[1])){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * overloaded method to ensure courses also within time range set by user
     * @param other
     * @param minStartTime
     * @param maxEndTime
     * @return boolean
     */
    
    /////
      
    private int spotRemaining = -1;
    private int waitRemaining = -1;
    
    public void checkGenerator() {

    	try {
        // creating arraylist of prof and gpa, by reading table

        /// Proper code: 
    	// call checkGenerator on course then check waitlist etc
    	String url= "https://oscar.gatech.edu/pls/bprod/bwckschd.p_disp_detail_sched?term_in="+term+"&crn_in="+crn;
        Document doc = Jsoup.connect(url).get();
        Element table = doc.select("table").get(4);
        Elements rows = table.select("tr"); //  gets rows in table
        Element rowOne = rows.get(1); // 1 gets second row which has all the info row1
        Element rowTwo = rows.get(2);

        Elements colsOne = rowOne.select("td");
        Elements colsTwo = rowTwo.select("td");

        spotRemaining = Integer.parseInt(colsOne.get(2).text());
        waitRemaining = Integer.parseInt(colsTwo.get(2).text());
    }
    	catch (Exception ex) {
    		System.out.println("errorin check"); //del
    		ex.printStackTrace();
    		
    		spotRemaining = -1;
    		waitRemaining = -1;
    	}
    }
    
    public int getSpotRemaining(){
        return spotRemaining;
    }

    public int getWaitRemaining(){
        return waitRemaining;
    }

    
    
    
    /////

    public boolean clash (Course other, int minStartTime, int maxEndTime) {
        for (int[] e: dayTime){
            for(int[] f: other.getDayTime()) {
                if ((e[0] <= f[0] && f[0] <= e[1])  || (e[0] <= f[1] && f[1] <= e[1])){
                    return true;
                }
            }
        }
        if(this.startTime < minStartTime  || this.endTime > maxEndTime ) {
            return true;
        }
        return false;
    }

}
