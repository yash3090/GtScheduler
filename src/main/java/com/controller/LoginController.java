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
    @Autowired
    LoginService service;
    @RequestMapping(value="/scheduleCreator", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "scheduleCreator"; // scheduleCreator jsp in webapp/WEB-INF
    }
    @RequestMapping(value="/scheduleCreator", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String courses, @RequestParam String startTime, @RequestParam String endTime, @RequestParam String wantedCRN, @RequestParam String unwantedCRN, @RequestParam String year, @RequestParam String Term, @RequestParam String openCheck ){
    	SortingAlgorithm a;
    	List<Course> b;
    	String term;
    	ArrayList<String[]> temp;
  
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
        	model.put("errorMessage", "Incorrect Course Name, Course Format, or Term");
        	return "scheduleCreator"; 
        }
    	
    	try {  // runs the code
    	a.sort();
    	b = a.getBestSchedule();
  
    	temp = a.getProfGPA();
    	} catch(Exception ex) {
    		model.put("errorMessage", "Incorrect Course Name or Course Format");
        	return "scheduleCreator"; 
    	}
    	
    	if ( b == null) {
    		model.put("errorMessage", "No schedules available with these constraints");
    		
    		return "scheduleCreator";
    	}
    	
        model.put("courses", b); 
        return "Welcome"; // page with schedule
    }
}

