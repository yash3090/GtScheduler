package com.controller
;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import com.controller.LoginService;


@Controller
@SessionAttributes("name")
public class LoginController {
	static public int counter = 0;
	int tempCounter;
	static List<Timetable> b;
	SortingAlgorithm a;
    @Autowired
    LoginService service;
    
    
    @RequestMapping(value="/scheduleCreator", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
    	RateMyProf.profRatingGenerator();
        return "scheduleCreator"; // scheduleCreator jsp in webapp/WEB-INF
        
    }
    
    
    @RequestMapping(value="/scheduleCreator", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String courses, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String wantedCRN, @RequestParam String unwantedCRN, @RequestParam String year, @RequestParam String Term){
    	
    
    	String term;
    
  
    	try {
    		term = year+Term;
        	if(!wantedCRN.equals("") && !unwantedCRN.equals("")) {
        		a = new SortingAlgorithm(courses.split(","), wantedCRN.split(","), unwantedCRN.split(","), startTime, endTime, term);
        	} else if (wantedCRN.equals("") && !unwantedCRN.equals("")) {
        		 a = new SortingAlgorithm(courses.split(","), new String[]{} , unwantedCRN.split(","), startTime, endTime, term);
        	} else if(!wantedCRN.equals("") && unwantedCRN.equals("")) {
        		 a = new SortingAlgorithm(courses.split(","), wantedCRN.split(","), new String[]{} , startTime, endTime, term);
        	} else {
        		 a = new SortingAlgorithm(courses.split(","), new String[]{}, new String[]{} , startTime, endTime, term);
        	}
                	
        	if (year.equals("")) {
        		model.put("errorMessage", "Please enter a year");
        		return "scheduleCreator";
        	}
        } catch (Exception ex) {
        	model.put("errorMessage", "Incorrect Course Name or Course Format at: " + ex.getMessage());
        	return "scheduleCreator";
        
        	
        }
    	
    	try {  // runs the code
    	//a.sort();
    	//b = a.getBestSchedule();
    	
    	
    	
    	
    	
    	b = new ArrayList<>(a.sort());
    	//b = new ArrayList<>(a.sortTimeTable());
    	
    	  
    	if ( b == null) {
    		model.put("errorMessage", "No schedules available with these constraints");
    		
    		return "scheduleCreator";
    	}
    	
    
  
    	
    	} catch(NullPointerException ex) {
    		model.put("errorMessage", "Incorrect Course Name or Course Format at: " + ex.getMessage());
  
        	return "scheduleCreator"; 
    	}catch(IndexOutOfBoundsException ex) {
    		model.put("errorMessage", "No schedules available with these constraints");
    		return "scheduleCreator";
    		
    	}
    	catch (Exception ex) {
    		model.put("errorMessage", "Please Check Timing Format");
        	return "scheduleCreator"; 
    	}
    	
    	
    	
        model.put("courses", b.get(0).timetable); // change .get() to count which changes when next clicked
        // changed it from 
        return "Welcome"; // page with schedule
    }
    

    
   
    
      @RequestMapping(value="/next", method = RequestMethod.GET)
      public String showNext(ModelMap model){
    	 
    	  
    	  counter = counter +1;
 
  		
	  		if (0 <=counter && counter+1 < b.size()){
	          model.put("courses", b.get(counter).timetable);
	          return "Welcome";
	  		} else {
	  		model.put("courses", b.get(counter).timetable);
	  		model.put("errorMessage", "No more schedules available");
	  		return "Welcome";
	  		}
	          
    	  
      }
      
      
      
      
      @RequestMapping(value="/prev", method = RequestMethod.GET)
      public String showPrev(ModelMap model){
  		
    	
    	  counter = counter -1;
    	  
    	 
    	  
  		
	  		if (0 <=counter && counter+1 < b.size()){
	          model.put("courses", b.get(counter).timetable);
	          return "Welcome";
	          } else {
	        	  model.put("courses", b.get(counter).timetable);
	        	  model.put("errorMessage", "This is the best Schedule available");
	        	  return "Welcome";
	          }
    	  
      }
   
}

