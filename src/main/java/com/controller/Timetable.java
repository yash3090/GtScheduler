package com.controller;
import java.util.ArrayList;
public class Timetable implements Comparable<Timetable> {
	
	public double total;
	public ArrayList<Course> timetable;
	ArrayList<String[]> profGpa;
	public Timetable(ArrayList<Course> a, ArrayList<String[]> profGpa) {
		this.profGpa = profGpa;
		timetable = new ArrayList<>(a);
		total = 0;
		
         for (Course section: a) {
        	 try {
        		 if (section instanceof Lecture) {
        			 total = total + ((Lecture)section).lectureRating(this.profGpa);
            	 
        		 }
        	 }catch(Exception ex) {
            	 System.out.println("Timetable error");
             }
          
                 
             }
     }
	
	
	@Override
	public int compareTo(Timetable cou) {		
		if ((this.total - cou.total) < 0) {
			return 1;
		} else if ((this.total - cou.total) >0){
			return -1;
		}
		return ((int)(this.total - cou.total)) ; // if diff -1<x<1 then same
		
	}


	

}
