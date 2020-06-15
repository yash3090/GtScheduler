package com.controller;


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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
public class Oscar {


    private String term;
    private String courseName;
    private String courseNum;
    private String url;

    /**
     * constructor
     * @param term has been set to default format 202008 in Sorting algorithm class, where constructor called
     * @param courseName format CS, ACC, PSYC, etc... Cs is wrong
     * @param courseNum number eg 1331,1332
     */

    public Oscar (String term, String courseName, String courseNum) { //
        this.term = term; // term has to be in format 2020008
        this.courseName = courseName;
        this.courseNum = courseNum;
        url = "https://oscar.gatech.edu/pls/bprod/bwckctlg.p_disp_listcrse?term_in="+this.term+"&subj_in="+this.courseName+"&crse_in="+this.courseNum+"&schd_in=%";

    }

    // add feature for diff term
    // eg 202008 --> fall
    // therefore year followed by term --> 08 = fall, 02 = spring, 05 = summer
    // this will change url

    ArrayList<ArrayList<Course>> tempo = new ArrayList<>();
    private ArrayList<Course> sectionsLecture = new ArrayList<>();
    private ArrayList<Course> sectionsStudio = new ArrayList<>();


    /**
     * getter method
     * @return Arraylist<Course> of all the possible lectures for a course // exception of sections with TBA times
     */

    public ArrayList<Course> getSectionsLecture() {
        return sectionsLecture;
    }

    /**
     * getter method
     * @return Arraylist<Course> of all possible labs/studios for a course
     */
    public ArrayList<Course> getSectionsStudio() {
        return sectionsStudio;
    }


    /**
     * Generates all the sections for a course and stores it into the respective Arraylists
     * Drawback: This method fails for courses with two timings in a day as it uses "-" to navigate through page, for eg fails for courses with common exams like phys 22__
     * @throws IOException if term/course name/ num wrong
     */

    public void sectionGenerator() throws IOException {

        // creating arraylist of prof and gpa, by reading table

        /// Proper code:

        Document doc = Jsoup.connect(url).get();
        Elements body = doc.select("body");
        String bodyPage = body.text(); // gives entire page
        int tableCounter = 4;

        do {

            Element table = doc.select("table").get(tableCounter); // 4 gives info table --> time, location, etc
            Elements rows = table.select("tr"); //  gets rows in table
            Element row = rows.get(1); // 1 gets second row which has all the info row1
            Elements cols = rows.select("td"); // gets into form col in 2nd row

            String time = cols.get(1).text();
            String days = cols.get(2).text();
            String location = cols.get(3).text();
            String dateRange = cols.get(4).text();
            String type = cols.get(5).text();
            String instructorName = cols.get(6).text();


            //col1 = class time, col2 = days, col3 = loc, col4 = start and end, col5 = type (lecture/studio), col6 = prof


            // getting crn, course eg CS 3600, section

            int crnStart = bodyPage.indexOf(" - ") + 3;
            int crnEnd = crnStart + 5;
            String crn = bodyPage.substring(crnStart, crnEnd);


            bodyPage = bodyPage.substring(crnEnd);
            int courseStart = bodyPage.indexOf(" - ") + 3;
            int courseEnd = bodyPage.indexOf(" - ", courseStart + 1);
            String course = bodyPage.substring(courseStart, courseEnd);

            System.out.println(course);

            bodyPage = bodyPage.substring(courseEnd + 3);
            int secEnd = bodyPage.indexOf(" ");
            String section = bodyPage.substring(0, secEnd);


            if (!time.equals("TBA")) {
                int bufferIndex1 = bodyPage.indexOf(" - ");
                bodyPage = bodyPage.substring(bufferIndex1 + 3);
            }

            int bufferIndex2 = bodyPage.indexOf(" - ");
            bodyPage = bodyPage.substring(bufferIndex2 + 3);
            tableCounter = tableCounter + 1;

            if (!time.equals("TBA")) {
                if (type.indexOf("Lectu") != -1) {
                    sectionsLecture.add(new Lecture(crn, section, courseName + courseNum, type, instructorName, location, time, days, term));
                } else {
                    sectionsStudio.add(new Studio(crn, section, courseName + courseNum, type, instructorName, location, time, days, term));
                }
            }

        } while(bodyPage.indexOf(" - ") != -1);





        // "https://oscar.gatech.edu/pls/bprod/bwckctlg.p_disp_listcrse?term_in=202008&subj_in=CS&crse_in=3600&schd_in=%"

    }
}

