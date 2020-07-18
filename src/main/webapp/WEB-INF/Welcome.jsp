<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@page import="com.controller.Course"%> 
     <%@page import="com.controller.SortingAlgorithm"%> 
     <%@page import="com.controller.Lecture"%> 
     <%@page import="com.controller.Studio"%> 
     <%@page import="com.controller.Timetable"%>
     <%@page import="java.util.ArrayList"%> 
      <%@page import="com.controller.RateMyProf"%> 
     
     
<!DOCTYPE html>
<html>
<head>
<style>
.container {
  position: relative;
}

.topright {
  position: absolute;
  top: 8px;
  right: 16px;
  font-size: 18px;
}
.topleft{
position: absolute;
  top: 8px;
  left: 16px;
  font-size: 18px;
  }

img { 
  width: 100%;
  height: auto;
  opacity: 0.3;
}
</style>

<meta charset="UTF-8">




<style>
body {
  background-image: url('/images/backone.jpeg');
  background-repeat: no-repeat;
  background-attachment: fixed;  
  background-size: cover;
}
</style>



<title>Schedule</title>
</head>
<body>

<br>
<center>
<font color="red" style="font-size:25px">${errorMessage}</font>
</center>



<div class="container">
 <div class="topleft"> <button id="prev" onclick="location.href ='/prev'" >Click to see the previous schedule</button> <%//try %>
</div>

<div class="container">
 <div class="topright"> <button id="next" onclick="location.href ='/next'" >Click to see the next best schedule</button> <%//try %></div>
</div>


<br>
<br>
<br>
    <center>
<table border ="1">

<tr>
<td style="color:Blue"> <b><center>Course Name</center></b> </td>
<td style="color:Blue"><b><center> Course Type</center></b> </td>
<td style="color:Blue"> <center><b>Timings </b></center></td>
<td style="color:Blue"> <b><center>Days</center></b> </td>
<td style="color:Blue"> <b><center>Professor</center></b> </td>
<td style="color:Blue"> <b><center>CRN</center></b> </td>
<td style="color:Blue"> <b><center>Rate My Professor Rating</center> </b></td>
<td style="color:Blue"><b> <center>Average GPA </center></b></td>
<td style="color:Blue"><b><center> # Waitlist Spots</center> </b></td>
<td style="color:Blue"><b><center> # Free Spots</center> </b></td>

</tr>

<% 

for(Course e: (ArrayList<Course>)request.getAttribute("courses")) { 
if(e instanceof Studio){
	e.checkGenerator();
}
%>
<tr>

<td><center><%= e.getCourseId() %></center></td>
<td><center><%= e.getCourseType() %> </center></td>
<td><center> <%= e.getTime() %></center></td>
<td><center> <%= e.getDays() %></center></td>
<td><center> <%=e.getProfessor() %></center></td>
<td><b><center><%= e.getCrn()%></center></b></td>
<td><b> <center><% if(e instanceof Lecture) {out.print(((Lecture)e).rmpRating());} else{out.print("N/A");} %></center></b></td>
<td> <b><center><% if(e instanceof Lecture) {out.print(((Lecture)e).GPAgetter());} else{out.print("N/A");} %></center> </b></td>
<td> <center><%= e.getWaitRemaining() %></center></td>
<td><center><%=e.getSpotRemaining() %></center></td>
</tr>

<%} %>

</table>
</center>
<br>

<center>

<Table>

<tr>
       
                    
                    <td><input type="radio" name="openCheck" onclick="location.href ='/sortlecture'"
                        value="one"><b> Select for Timetables with sections which only have free slots</b> </td>
                        
                    <td><input type="radio" name="openCheck" value="both" onclick="location.href ='/sortboth'"
					    checked> <b> Select for Timetables with sections which have free slots or seats on waitlist</b></td>
					    
					
                   
                </tr>
                
         </Table>


</center>

<br>
<p> <h3> if waitlist/free spot is -1 then the algorithm can't check individual section's availability, due to the large number of sections for the course</h3>

</body>
</html>