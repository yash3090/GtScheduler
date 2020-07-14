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
<title>Schedule</title>
</head>
<body>

<br>
<center>
<font color="red" style="font-size:25px">${errorMessage}</font>
</center>

<body style="background-color:SkyBlue;">

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
<td style="color:#CC6600"> <b>Course Name</b> </td>
<td style="color:#cc6600"><b> Course Type</b> </td>
<td style="color:#cc6600"> <b>Timings </b></td>
<td style="color:#CC6600"> <b>Days</b> </td>
<td style="color:#CC6600"> <b>Professor</b> </td>
<td style="color:#CC6600"> <b>CRN</b> </td>
<td style="color:#CC6600"> <b>Rate My Professor Rating </b></td>
<td style="color:#CC6600"><b> Average GPA </b></td>
<td style="color:#CC6600"><b> # Waitlist Spots </b></td>
<td style="color:#CC6600"><b> # Free Spots </b></td>

</tr>

<% 
b = (ArrayList<Timetable>)request.getAttribute("possibleTimetables");
for(int i = 0;i< b.size();i++){
	for (Course c: b.get(i).timetable){
		c.checkGenerator();
	}
	
}

for(Course e: (ArrayList<Course>)request.getAttribute("courses")) { 

%>
<tr>

<td> <%= e.getCourseId() %></td>
<td><%= e.getCourseType() %> </td>
<td> <%= e.getTime() %></td>
<td> <%= e.getDays() %></td>
<td> <%=e.getProfessor() %>
<td><b><%= e.getCrn()%></b></td>
<td> <% if(e instanceof Lecture) {out.print(((Lecture)e).rmpRating());} else{out.print("N/A");} %></td>
<td> <% if(e instanceof Lecture) {out.print(((Lecture)e).GPAgetter());} else{out.print("N/A");} %> </td>
<td> <%= e.getWaitRemaining() %>
<td><%=e.getSpotRemaining() %>
</tr>

<%} %>

</table>
</center>
<br>
<center>


</center>

</body>
</html>