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
    public String showWelcomePage(ModelMap model, @RequestParam String courses, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String wantedCRN, @RequestParam String unwantedCRN, @RequestParam String year, @RequestParam String Term, @RequestParam String openCheck ){
    	
    
    	String term;
    
  
    	try {
    		term = year+Term;
        	if(!wantedCRN.equals("") && !unwantedCRN.equals("")) {
        		a = new SortingAlgorithm(courses.split(","), wantedCRN.split(","), unwantedCRN.split(","), startTime, endTime, term, openCheck);
        	} else if (wantedCRN.equals("") && !unwantedCRN.equals("")) {
        		 a = new SortingAlgorithm(courses.split(","), new String[]{} , unwantedCRN.split(","), startTime, endTime, term, openCheck);
        	} else if(!wantedCRN.equals("") && unwantedCRN.equals("")) {
        		 a = new SortingAlgorithm(courses.split(","), wantedCRN.split(","), new String[]{} , startTime, endTime, term, openCheck);
        	} else {
        		 a = new SortingAlgorithm(courses.split(","), new String[]{}, new String[]{} , startTime, endTime, term, openCheck);
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
    	a.sort();
    	//b = a.getBestSchedule();
    	
    	
    	
    	
    	
    	
    	b = new ArrayList<>(a.sortTimeTable());
    	
    	  
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
  		
  		if (0 <=counter && counter+1 < b.size()){
  			
  			counter= counter+1;//del
  			System.out.println(counter);
        
          model.put("courses", b.get(counter).timetable);
          return "Welcome";
          } else {
        	  model.put("courses", b.get(counter).timetable);
        	  model.put("errorMessage", "No more schedules with these constraints");
        	  return "Welcome";
          }
      }
      
      
      @RequestMapping(value="/prev", method = RequestMethod.GET)
      public String showPrev(ModelMap model){
  		
  		if (0 <=counter-1 && counter <= b.size()){
  			counter= counter-1;
  			System.out.println(counter);//del
        
          model.put("courses", b.get(counter).timetable);
          return "Welcome";
          } else {
        	  model.put("courses", b.get(counter).timetable);
        	  model.put("errorMessage", "Can't go back. This is the best possible schedule");
        	  return "Welcome";
          }
      }
   
}

