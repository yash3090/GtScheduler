<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 
    
 
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<h1 style="color:gold"> <b><center>Georgia Tech Schedule Creator</center></b></h1>
<meta charset="UTF-8">
<title>Schedule Creator</title>
</head>
<body style="background-color:black;">

<center>
<p style="color:white"> The algorithm scrapes data from RateMyProfessor and CourseCritque to create the <b><u>best</u></b> schedule for you, automatically syncing lectures with studios/labs <br> 
<br><font style="font-size:15px">Hover over rightmost column for details on what to enter in each text field. It may take between 2-3 minutes to generate a timetable</font>
<br>

</p>
 </center>
<center>

<font color="red" style="font-size:25px">${errorMessage}</font>
  </center>
  
  
  <style>
a:link {
  color: white;
  background-color: transparent;
  text-decoration: none;
}

a:visited {
  color: white;
  background-color: transparent;
  text-decoration: none;
}

a:hover {
  color: LightGreen ;
  background-color: transparent;
  text-decoration: underline;
}

a:active {
  color: white;
  background-color: transparent;
  text-decoration: underline;
}
</style>
  
  
  <center>
    <form method="post">
    <table class="center">
    <tr width="100%">
        <td><p style="color:gold"> Courses :</p></td> 
        <td style="color:white"><input type="text" name="courses" /> <a href="#" data-toggle="tooltip" data-placement="right" title="enter with only ' , ' separating courses eg: MATH 1554,CS 1332,ECE 2035"> * Mandatory Field</a></td>
        </tr>
    
   
     
    
      
        <tr width="100%">
        <td><p style="color:gold"> Start time :</p> </td>
        <td style="color:white"> <input type="text" name="startTime" /> <a href="#" data-toggle="tooltip" data-placement="right" title="Time should be in 2400 format, eg: 1200">* Optional Field</a> </td>
        </tr>
        
        
      
      
        
        <tr width="100%">
        <td><p style="color:gold"> End time : </p></td>
        <td style="color:white"> <input type ="text" name = "endTime"/> <a href="#" data-toggle="tooltip" data-placement="right" title="Time should be in 2400 format, eg: 0900">* Optional Field</a> </td>
        </tr>
        
       
      
        
        <tr width="100%">
        <td><p style="color:gold"> Wanted CRN : </p></td>
        <td style="color:white"> <input type="text" name = "wantedCRN"/>  <a href="#" data-toggle="tooltip" data-placement="right" title="If you want specific sections enter the CRN's without any spaces eg: 89989,99999,0000. Note: CRN must be of a course entered above and only one wanted CRN per course">* Optional Field</a></td>
        </tr>
      
       
        
        <tr width="100%">
        <td> <p style="color:gold"> Unwanted CRN : </p></td>
        <td style="color:white"><input type="text" name = "unwantedCRN" />  <a href="#" data-toggle="tooltip" data-placement="right" title="If you dont want specific sections enter the CRNs.
Note: There can be multiple CRNs for one course, this field can be used for removing sections which take place at a different campus">* Optional Field</a></td>
        
        </tr>
     
        <br>
        
        
        <tr width="100%">
        <td> <p style="color:gold"> Year :</p> </td>
        <td style="color:white"> <input type="text" name = "year" /> <a href="#" data-toggle="tooltip" data-placement="right" title="eg: 2020">* Mandatory Field</a></td>
        </tr>
        </Table>
        
        <br>
        <Table>
        <tr>
                    <td width="20%"><p style="color:gold"> Term : </p></td>
                    
                    <td width="30%" style="color:gold"><input type="radio" name="Term" 
                        value="05"> Summer </td>
                    <td width="20%" style="color:gold"><input type="radio" name="Term" value="08" 
					    checked> Fall</td>
                    <td width="30%" style="color:gold"><input type="radio" name="Term" value="02">
					   Spring</td>
                </tr>
                </Table>
                <br>
                <Table>
                
                <tr width="100%">
       
                    
                    <td width="25%" style="color:gold"><input type="radio" name="openCheck" 
                        value="one"> Lectures which only have free slots </td>
                        
                    <td width="30%" style="color:gold"><input type="radio" name="openCheck" value="both" 
					    checked> Lectures which have slots open or seats on waitlis</td>
					    
					    <td width="30%" style="color:gold"><input type="radio" name="openCheck" value="blah" 
					    checked>Don't take into account availability of seats</td>
                   
                </tr>
                
         </Table>
         <br>
         <br>
        <input type="submit" />
 
    </form>
    
  </center>
<script>
$(document).ready(function(){
  $('[data-toggle="tooltip"]').tooltip();   
});
</script>


<body>
      <marquee style="color:SkyBlue">For feedback/suggestions email at: gtschedulecreator@gmail.com</marquee>
   </body>
</body>
</html>