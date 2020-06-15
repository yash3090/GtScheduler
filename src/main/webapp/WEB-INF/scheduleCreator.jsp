<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 
<!DOCTYPE html>
<html>
<head>
<h1>Georgia Tech Customizable Schedule Creator</h1>
<meta charset="UTF-8">
<title>Schedule Creator</title>
</head>
<body>

<p> The algorithm scrapes data from RateMyProfessor and CourseCritque to create the Highest Average Course Rating schedule for you. <br>
You can customize the schedule to best suit your needs through the constraints below.
<br> The app can't create schedules for courses like Physics ____ which have a common exam time slot
</p>

<font color="red">${errorMessage}</font>
    <form method="post">
    <Table>
    <tr width="100%">
        <td>Courses :</td> 
        <td><input type="text" name="courses" /> * Mandatory Field (enter with only "," separating courses eg: MATH 1554,CS 1332,ECE 2035) </td>
        </tr>
    
     
     
    
      
        <tr width="100%">
        <td>Start time : </td>
        <td> <input type="text" name="startTime" /> * Optional Field (in 2400 form, eg: 1200) </td>
        </tr>
        
        
      
      
        
        <tr width="100%">
        <td>End time : </td>
        <td> <input type ="text" name = "endTime"/> * Optional Field (if no value entered, then no time constraints) </td>
        </tr>
        
       
      
        
        <tr width="100%">
        <td>Wanted CRN : </td>
        <td> <input type="text" name = "wantedCRN"/> * Optional Field (If you want specific sections enter the CRN's without any spaces, eg: 00000). Note: CRN must be of a course entered above</td>
        </tr>
      
       
        
        <tr width="100%">
        <td>Unwanted CRN : </td>
        <td><input type="text" name = "unwantedCRN" /> * Optional Field (same format as above eg: 89989,99999,00000) </td>
        
        </tr>
     
        <br>
        
        
        <tr width="100%">
        <td>Year : </td>
        <td> <input type="text" name = "year" /> * Mandatory Field</td>
        </tr>
        </Table>
        
        <br>
        <Table>
        <tr>
                    <td width="20%">Term :</td>
                    
                    <td width="30%"><input type="radio" name="Term" 
                        value="05">Summer</td>
                    <td width="20%"><input type="radio" name="Term" value="08" 
					    checked>Fall</td>
                    <td width="30%"><input type="radio" name="Term" value="02">
					    Spring</td>
                </tr>
                </Table>
                <br>
                <Table>
                
                <tr width="100%">
       
                    
                    <td width="25%"><input type="radio" name="openCheck" 
                        value="one"> Lectures which only have free slots </td>
                        
                    <td width="30%"><input type="radio" name="openCheck" value="both" 
					    checked> Lectures which have slots open or seats on waitlist</td>
					    
					    <td width="30%"><input type="radio" name="openCheck" value="blah" 
					    checked>Don't take into account availability of seats</td>
                   
                </tr>
                
         </Table>
         <br>
        <input type="submit" />
 
    </form>


</body>
</html>