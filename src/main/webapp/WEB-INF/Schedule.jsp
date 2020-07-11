<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
    
 <%@page import="com.controller.Course"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
hi
<font color="red">${errorMessage}</font>

<form>
<input type="button" value = "previous" name="iter"/>
<input type="button" value = "next" name="iter"/>
</form>

</body>
</html>