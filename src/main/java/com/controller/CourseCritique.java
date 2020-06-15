package com.controller;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Comparator;

public class CourseCritique {

    private String courseName;
    private String courseNum;
    private String FinalURL;

    // arraylist containing all the names of prof and their avg gpa for a particular course
    // must call profAvgGpaGenerator first so it isnt empty
    public ArrayList<String[]> name_rating = new ArrayList<>(); // name is lastname, firstname as one



    /**
     * constructor for Coursecritique
     *
     * @param CourseID in form CS1332, ACC____, etc.
     */
    public CourseCritique(String courseName, String courseNum) { // has to be in form of no space
        this.courseName = courseName;
        this.courseNum = courseNum;
        this.FinalURL = "https://critique.gatech.edu/course.php?id="+this.courseName+"%20"+this.courseNum;

    }


    /**
     * getter method for name_rating
     * @return
     */
    public ArrayList<String[]> getNameRating() {
        return name_rating;
    }

    /**
     * this method goes to course critique and creates an Arraylist with String arrays consisting of profName, rating.
     * The method also sorts the arraylist such that the prof with the highest gpa is first followed by second ....
     *
     * @throws IOException if the course name is incorrect eg Cs1332 --> could rename the error?
     */
    public void profAvgGpaGenerator() throws IOException {

        // creating arraylist of prof and gpa, by reading table

        Document doc = Jsoup.connect(FinalURL).get();
        ArrayList<String> downServers = new ArrayList<>();
        Element table = doc.select("table").get(1); //select the first table.
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
            Element row = rows.get(i);
            Elements cols = row.select("td");
            String[] temp = {cols.get(0).text(), cols.get(2).text()};
            name_rating.add(temp);
        }


        // sorting the arraylist
        // dont neeed it

        boolean needNextPass = true;
        for (int k = 1; k < name_rating.size() && needNextPass; k++) { // Array may be sorted and next pass not needed needNextPass = false;
            for (int z = 0; z < name_rating.size() - k; z++) {
                if (Float.parseFloat((name_rating.get(z))[1]) < Float.parseFloat((name_rating.get(z + 1))[1])) {
                    String[] temp = name_rating.get(z);
                    name_rating.set(z, name_rating.get(z + 1));
                    name_rating.set(z + 1, temp);
                    needNextPass = true;
                }
            }
        }

    }
}