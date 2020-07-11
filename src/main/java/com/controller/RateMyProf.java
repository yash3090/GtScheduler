package com.controller;

import java.io.IOException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
public class RateMyProf {

    public static ArrayList<String[]> tempprofessorlist = new ArrayList(); // arraylist of String[] with firtsname, middlename, lastname, rating :
    // IMP call method profRatingGenerator first so that array isnt empty


    public RateMyProf(){
    }

    /**
     * Helper method for profRatingGenerator
     * gives number of professors so we can calculate how many pages of professors are there for gatech on rateMyProf webpage
     * @return total number of prof in gatech on RateMyProf
     * @throws Exception
     */
    public static int profCounter() throws Exception{
        String a = "";
        URL oracle = new URL("https://www.ratemyprofessors.com/filter/professor/?&page=40&filter=teacherlastname_sort_s+asc&query=*%3A*&queryoption=TEACHER&queryBy=schoolDetails&schoolID=361&schoolName=Georgia+Institute+of+Technology"); // URL to Parse
        
        //https://www.ratemyprofessors.com/filter/professor/?&page=7&filter=teacherlastname_sort_s+asc&query=*%3A*&queryoption=TEACHER&queryBy=schoolDetails&schoolID=361&schoolName=Georgia+Institute+of+Technology
        oracle.openStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            a = a + inputLine;
        }
        int start = a.indexOf("searchResultsTotal");
        a = a.substring(start);
        int end = a.indexOf(",");
        int totalProf = Integer.parseInt(a.substring(20,end));
        return totalProf;
    }

    /**
     * Adds to arraylist #tempprofessorlist# String[] with all the prof {firstname, middlename, lastname, rating}
     * note the rating can be N/A
     * @throws Exception if URL doesnt exist // however it always does --> java quirk
     */

    public static void profRatingGenerator() {
    	int i = 1;
        try {
        	
            //int num_of_prof = profCounter();
            //System.out.println(num_of_prof); //del
        	
        	
        	
            int num_of_pages = 150; //(int) (num_of_prof / 20.0); //change to 150 after debuggin
            
            
            
            
            
            //System.out.print(num_of_pages);//del
            
            while (i <= num_of_pages) { // the loop insert all professor into list change 1 to numpages
                boolean checker = true;
                String finalUrl = "https://www.ratemyprofessors.com/filter/professor/?&page="+Integer.toString(i)+"&filter=teacherlastname_sort_s+asc&query=*%3A*&queryoption=TEACHER&queryBy=schoolId&sid=361";
                
                //https://www.ratemyprofessors.com/filter/professor/?&page=64&filter=teacherlastname_sort_s+asc&query=*%3A*&queryoption=TEACHER&queryBy=schoolId&sid=361
                String a = "";    
                URL oracle = new URL(finalUrl); // URL to Parse
                oracle.openStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    a = a + inputLine;
                }
                String tempString = a;
                
                
                
                
                if (i<=50) {
                String finalUrlRev = "https://www.ratemyprofessors.com/filter/professor/?&page="+Integer.toString(i)+"&filter=teacherlastname_sort_s+desc&query=*%3A*&queryoption=TEACHER&queryBy=schoolId&sid=361";
                String b="";
                URL oracle1 = new URL(finalUrlRev);
                BufferedReader inRev = new BufferedReader(new InputStreamReader(oracle1.openStream()));
                String inputLineRev;
                while ((inputLineRev = inRev.readLine()) != null) {
                    b = b + inputLineRev;
                }
                String tempStringRev = b;
                while (checker) {
                    int tempIndex = tempStringRev.indexOf("tFname");
                    tempStringRev = tempStringRev.substring(tempIndex);
                    int tempIndexStart = tempStringRev.indexOf(":");
                    int tempIndexEnd = tempStringRev.indexOf(",");
                    String fName = tempStringRev.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempIndex = tempStringRev.indexOf("tMiddlename");
                    tempStringRev = tempStringRev.substring(tempIndex);
                    tempIndexStart = tempStringRev.indexOf(":");
                    tempIndexEnd = tempStringRev.indexOf(",");
                    String mName = tempStringRev.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempIndex = tempStringRev.indexOf("tLname");
                    tempStringRev = tempStringRev.substring(tempIndex);
                    tempIndexStart = tempStringRev.indexOf(":");
                    tempIndexEnd = tempStringRev.indexOf(",");
                    String lName = tempStringRev.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempIndex = tempStringRev.indexOf("overall_rating");
                    tempStringRev = tempStringRev.substring(tempIndex);
                    tempIndexStart = tempStringRev.indexOf(":");
                    tempIndexEnd = tempStringRev.indexOf("}");
                    String StrRating = tempStringRev.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempprofessorlist.add(new String[]{fName, mName, lName, StrRating});
                    
                 

                    if (tempStringRev.indexOf("ResultsTotal") < 50) {  // checks when end of the lists of prof
                        checker = false;
                    }
                
                
                }
                }
                
                System.out.println(checker);
                
                checker=true;
                
                while (checker) {
                    int tempIndex = tempString.indexOf("tFname");
                    tempString = tempString.substring(tempIndex);
                    int tempIndexStart = tempString.indexOf(":");
                    int tempIndexEnd = tempString.indexOf(",");
                    String fName = tempString.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempIndex = tempString.indexOf("tMiddlename");
                    tempString = tempString.substring(tempIndex);
                    tempIndexStart = tempString.indexOf(":");
                    tempIndexEnd = tempString.indexOf(",");
                    String mName = tempString.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempIndex = tempString.indexOf("tLname");
                    tempString = tempString.substring(tempIndex);
                    tempIndexStart = tempString.indexOf(":");
                    tempIndexEnd = tempString.indexOf(",");
                    String lName = tempString.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempIndex = tempString.indexOf("overall_rating");
                    tempString = tempString.substring(tempIndex);
                    tempIndexStart = tempString.indexOf(":");
                    tempIndexEnd = tempString.indexOf("}");
                    String StrRating = tempString.substring(tempIndexStart + 2, tempIndexEnd - 1);

                    tempprofessorlist.add(new String[]{fName, mName, lName, StrRating});
                    
                 

                    if (tempString.indexOf("ResultsTotal") < 50) {  // checks when end of the lists of prof
                        checker = false;
                    }
                    
                    
                    
                    
                }
                
                System.out.println(i);
                i += 1;
                
                
                
                // https://www.ratemyprofessors.com/filter/professor/?&page=2&filter=teacherlastname_sort_s+desc&query=*%3A*&queryoption=TEACHER&queryBy=schoolId&sid=361
                // above link starts from z
            }
        } catch(Exception ex) {
            System.out.println("RateMyProf rating generator error");
            
        }
    }

    /**
     * Method which takes in prof name and returns rating
     */


}


