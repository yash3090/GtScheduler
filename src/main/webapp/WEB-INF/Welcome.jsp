<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@page import="com.controller.Course"%> 
     <%@page import="com.controller.SortingAlgorithm"%> 
     <%@page import="com.controller.Lecture"%> 
     <%@page import="com.controller.Studio"%> 
     <%@page import="java.util.ArrayList"%> 
      <%@page import="com.controller.RateMyProf"%> 
     
     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Schedule</title>
</head>
<body>

<table border ="1">

<tr>
<td> Course Name </td>
<td> Course Type </td>
<td> Timings </td>
<td> Days </td>
<td> Professor </td>
<td> CRN </td>
<td> Rate My Professor Rating </td>
<td> Average GPA </td>

</tr>

<% RateMyProf.profRatingGenerator();
for(Course e: (ArrayList<Course>)request.getAttribute("courses")){ %>
<tr>
<td><%= e.getCourseId() %></td>
<td><%= e.getCourseType() %> </td>
<td> <%= e.getTime() %></td>
<td> <%= e.getDays() %></td>
<td> <%=e.getProfessor() %>
<td><%= e.getCrn()%></td>
<td> <% if(e instanceof Lecture) {out.print(((Lecture)e).rmpRating());} else{out.print("N/A");} %></td>
<td> <% if(e instanceof Lecture) {out.print(((Lecture)e).GPAgetter());} else{out.print("N/A");} %> </td>
</tr>

<%} %>

</table>

</body>
</html>